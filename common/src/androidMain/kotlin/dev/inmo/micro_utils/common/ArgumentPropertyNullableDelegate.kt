package dev.inmo.micro_utils.common

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import java.io.Serializable
import kotlin.reflect.KProperty

object ArgumentPropertyNullableDelegate {
    operator fun <T: Any> getValue(thisRef: Fragment, property: KProperty<*>): T? {
        val arguments = thisRef.arguments ?: return null
        val key = property.name
        return when (property.getter.returnType.classifier) {
            // Scalars
            String::class -> arguments.getString(key)
            Boolean::class -> arguments.getBoolean(key)
            Byte::class -> arguments.getByte(key)
            Char::class -> arguments.getChar(key)
            Double::class -> arguments.getDouble(key)
            Float::class -> arguments.getFloat(key)
            Int::class -> arguments.getInt(key)
            Long::class -> arguments.getLong(key)
            Short::class -> arguments.getShort(key)

            // References
            Bundle::class -> arguments.getBundle(key)
            CharSequence::class -> arguments.getCharSequence(key)
            Parcelable::class -> arguments.getParcelable(key)

            // Scalar arrays
            BooleanArray::class -> arguments.getBooleanArray(key)
            ByteArray::class -> arguments.getByteArray(key)
            CharArray::class -> arguments.getCharArray(key)
            DoubleArray::class -> arguments.getDoubleArray(key)
            FloatArray::class -> arguments.getFloatArray(key)
            IntArray::class -> arguments.getIntArray(key)
            LongArray::class -> arguments.getLongArray(key)
            ShortArray::class -> arguments.getShortArray(key)
            Array::class -> {
                val componentType = property.returnType.classifier ?.javaClass ?.componentType!!
                @Suppress("UNCHECKED_CAST") // Checked by reflection.
                when {
                    Parcelable::class.java.isAssignableFrom(componentType) -> {
                        arguments.getParcelableArray(key)
                    }
                    String::class.java.isAssignableFrom(componentType) -> {
                        arguments.getStringArray(key)
                    }
                    CharSequence::class.java.isAssignableFrom(componentType) -> {
                        arguments.getCharSequenceArray(key)
                    }
                    Serializable::class.java.isAssignableFrom(componentType) -> {
                        arguments.getSerializable(key)
                    }
                    else -> {
                        val valueType = componentType.canonicalName
                        throw IllegalArgumentException(
                            "Illegal value array type $valueType for key \"$key\""
                        )
                    }
                }
            }
            Serializable::class -> arguments.getSerializable(key)
            else -> null
        } as? T
    }
}

object ArgumentPropertyNonNullableDelegate {
    operator fun <T: Any> getValue(thisRef: Fragment, property: KProperty<*>): T {
        return ArgumentPropertyNullableDelegate.getValue<T>(thisRef, property)!!
    }
}

fun argumentOrNull() = ArgumentPropertyNullableDelegate
fun argumentOrThrow() = ArgumentPropertyNonNullableDelegate
