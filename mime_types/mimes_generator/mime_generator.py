import math

import requests
from bs4 import BeautifulSoup
import pandas as pd
import itertools

def fix_name(category, raw_name):
    splitted = raw_name.replace('-', '+').replace('.', '+').replace(',', '+').split('+')
    out1 = ""
    for s in splitted:
        out1 += s.capitalize()

    result = ""
    if out1[0].isdigit():
        result += category[0].capitalize()
        result += out1
    else:
        result += out1
    return result

def remove_prefix(text, prefix):
    if text.startswith(prefix):
        return text[len(prefix):]
    return text  # or whatever

def extensionPreparationFun(extension):
    return "\"%s\"" % (remove_prefix(extension, "."))

# https://www.freeformatter.com/mime-types-list.html
if __name__ == '__main__':
    df = pd.read_html(open('local.table.html', 'r'))
    mimes = []
    for row in df[0].drop_duplicates(subset=['MIME Type / Internet Media Type'], keep='first').iterrows():
        mime = row[1][1]
        extensions = list()
        if isinstance(row[1][2], str):
            extensions = list(map(extensionPreparationFun, row[1][2].split(", ")))
        mime_category = mime.split('/', 1)[0]
        mime_name = mime.split('/', 1)[1]
        mimes.append([
            mime_category,
            mime_name,
            extensions
        ])

    # codegen

    mimes.sort(key=lambda x: x[0])
    grouped = itertools.groupby(mimes, lambda x: x[0])
    code = ''
    code2 = 'internal val knownMimeTypes: Set<MimeType> = setOf(\n'
    code2 += '    KnownMimeTypes.Any,\n'
    for key, group in grouped:
        group_name = fix_name(group, key)
        code += '@Serializable(MimeTypeSerializer::class)\nsealed class %s(raw: String, extensions: Array<String> = emptyArray()) : MimeType, KnownMimeTypes(raw, extensions) {\n' % group_name
        code += '    @Serializable(MimeTypeSerializer::class)\n    object Any: %s ("%s/*")\n' % (group_name, key)
        for mime in group:
            name = fix_name(mime[0], mime[1])
            code += '    @Serializable(MimeTypeSerializer::class)\n    object %s: %s ("%s/%s", arrayOf(%s))\n' % (name, group_name, mime[0], mime[1], ", ".join(mime[2]))
            code2 += '    KnownMimeTypes.%s.%s,\n' % (group_name, name)
        code += '}\n\n'
    code2 += ')\n'
    with open('out1.txt', 'w') as file:
        file.write(code)
    with open('out2.txt', 'w') as file:
        file.write(code2)
