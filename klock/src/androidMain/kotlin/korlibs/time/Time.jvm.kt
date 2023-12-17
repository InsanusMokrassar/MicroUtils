@file:Suppress("PackageDirectoryMismatch")
package korlibs.time.internal

import korlibs.time.*
import java.util.Date

fun Date.toDateTime() = DateTime(this.time)
fun DateTime.toDate() = Date(this.unixMillisLong)
