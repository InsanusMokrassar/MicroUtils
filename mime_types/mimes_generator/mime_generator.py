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

if __name__ == '__main__':
    df = pd.read_html(open('table.html', 'r'))
    mimes = []
    for row in df[0].iterrows():
        mime = row[1][1]
        mime_category = mime.split('/', 1)[0]
        mime_name = mime.split('/', 1)[1]
        mimes.append({
            'mime_category': mime_category,
            'mime_name': mime_name,
        })

    # codegen

    mimes.sort(key=lambda x: x['mime_category'])
    grouped = itertools.groupby(mimes, lambda x: x['mime_category'])
    code = ''
    code2 = 'internal val knownMimeTypes: Set<MimeType> = setOf(\n'
    code2 += '    KnownMimeTypes.Any,\n'
    for key, group in grouped:
        group_name = key.capitalize()
        code += '@Serializable(MimeTypeSerializer::class)\nsealed class %s(raw: String) : MimeType, KnownMimeTypes(raw) {\n' % group_name
        code += '    @Serializable(MimeTypeSerializer::class)\n    object Any: %s ("%s/*")\n' % (group_name, key)
        for mime in group:
            name = fix_name(mime['mime_category'], mime['mime_name'])
            code += '    @Serializable(MimeTypeSerializer::class)\n    object %s: %s ("%s/%s")\n' % (name, group_name, mime['mime_category'], mime['mime_name'])
            code2 += '    KnownMimeTypes.%s.%s,\n' % (group_name, name)
        code += '}\n\n'
    code2 += ')\n'
    with open('out1.txt', 'w') as file:
        file.write(code)
    with open('out2.txt', 'w') as file:
        file.write(code2)
