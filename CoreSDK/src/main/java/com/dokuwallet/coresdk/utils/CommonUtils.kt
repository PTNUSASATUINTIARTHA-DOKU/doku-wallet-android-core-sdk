package com.dokuwallet.coresdk.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import com.dokuwallet.coresdk.R
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.math.BigInteger
import java.nio.charset.StandardCharsets
import java.security.KeyFactory
import java.security.MessageDigest
import java.security.Signature
import java.security.spec.PKCS8EncodedKeySpec
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Base64
import java.util.Date
import java.util.Locale
import java.util.TimeZone
import java.util.zip.ZipEntry
import java.util.zip.ZipOutputStream
import javax.crypto.Cipher
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec
import kotlin.experimental.and

object CommonUtils {
    fun generatedHmacSha256(plainText: String, secretKey: String): String {
        // Encryption
        val keySpec = SecretKeySpec(secretKey.toByteArray(), "HmacSHA256")
        val mac = Mac.getInstance("HmacSHA256")
        mac.init(keySpec)

        return mac.doFinal(plainText.toByteArray())
            .joinToString("") { String.format("%02x", it and 255.toByte()) }

    }

    fun generateHmacSha1(plainText: String, secretKey: String): String {
        val keySpec = SecretKeySpec(secretKey.toByteArray(), "HmacSHA1")
        val mac = Mac.getInstance("HmacSHA1")
        mac.init(keySpec)

        return mac.doFinal(plainText.toByteArray())
            .joinToString("") { String.format("%02x", it and 255.toByte()) }
    }

    fun getCurrentTimestamp(formatType: Int = 1): String {
        val tz: TimeZone =
            if (formatType == 1) TimeZone.getTimeZone("GMT") else TimeZone.getTimeZone("GMT+7:00")
        val df: DateFormat =
            SimpleDateFormat(
                if (formatType == 1) "yyyy-MM-dd'T'HH:mm:ss'Z'" else "yyyy-MM-dd'T'HH:mm:ssZZZZZ",
                Locale.getDefault()
            )
        df.timeZone = tz
        return df.format(Date())
    }

    fun generateSHA256(buf: ByteArray): String {
        val bytes = MessageDigest.getInstance("SHA-256").digest(buf)
        return BigInteger(1, bytes).toString(16).padStart(64, '0')
    }

    fun toBase64(input: String): String {
        val bytes = input.chunked(2).map { it.toInt(16).toByte() }.toByteArray()
        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            Base64.getEncoder().encodeToString(bytes)
        } else {
            android.util.Base64.encodeToString(bytes, android.util.Base64.DEFAULT)
        }
    }

    fun encodeBitmapToBase64(bitmap: Bitmap): String {
        val bos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 10, bos)
        val byteArray = bos.toByteArray()
        return android.util.Base64.encodeToString(byteArray, android.util.Base64.DEFAULT)
            .replace("\n", "")
    }

    fun base64ToZip(base64: String, zipFile: File, fileName: String) {
        val zipBytes: ByteArray = base64.toByteArray()
        val fos = FileOutputStream(zipFile)
        val zos = ZipOutputStream(fos)
        val entry = ZipEntry(fileName.plus(".txt"))
        entry.size = zipBytes.size.toLong()
        zos.putNextEntry(entry)
        zos.write(zipBytes)
        zos.closeEntry()
        zos.close()
        fos.close()
    }

    fun convertColorCode(colorCode: String): String {
        if (colorCode.length == 7) return colorCode
        val sixCharColorCode = if (colorCode.length == 4) {
            val r = colorCode[1].toString()
            val g = colorCode[2].toString()
            val b = colorCode[3].toString()
            "#$r$r$g$g$b$b"
        } else {
            colorCode
        }

        return sixCharColorCode
    }

    fun showLoadingDialog(context: Context): Dialog {
        val progressDialog = Dialog(context)

        progressDialog.apply {
            setContentView(R.layout.doku_sdk_progress_dialog)

            if (window != null) {
                window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            }
            setCancelable(false)
            setCanceledOnTouchOutside(false)
            show()
        }
        return progressDialog
    }

    fun generateRandomNumber(length: Int): String {
        val randomNumber = StringBuilder()

        repeat(length) {
            val digit = kotlin.random.Random.nextInt(0, 10)
            randomNumber.append(digit)
        }

        return randomNumber.toString()
    }

    fun sha256WithRSA(stringToSign: String, privateKeyString: String): String {
        val keySpec = PKCS8EncodedKeySpec(
            android.util.Base64.decode(
                privateKeyString,
                android.util.Base64.NO_WRAP
            )
        )
        val keyFactory = KeyFactory.getInstance("RSA")
        val privateKey = keyFactory.generatePrivate(keySpec)
        val signatureLib = Signature.getInstance("SHA256withRSA")
        signatureLib.initSign(privateKey)
        signatureLib.update(stringToSign.toByteArray(Charsets.UTF_8))
        return android.util.Base64.encodeToString(signatureLib.sign(), android.util.Base64.NO_WRAP)
            .trim()
    }

    fun stringToSignToken(clientKey: String, timestamp: String): String = "$clientKey|$timestamp"

    fun <T> stringToSign(
        b2bToken: String,
        requestBody: T,
        httpMethod: String,
        endpointUrl: String,
        timestamp: String
    ): String {
        val gson = GsonBuilder().disableHtmlEscaping().create()
        val minifyJsonObject = gson.toJson(requestBody).toString().trim()

        val bodySha256 = calculateSHA256(minifyJsonObject).lowercase()

        return "$httpMethod:$endpointUrl:$b2bToken:$bodySha256:$timestamp"
    }

    fun hmacSha512(stringToSign: String, clientSecret: String): String {
        val signatureUtf8 = stringToSign.toByteArray(StandardCharsets.UTF_8)
        val secretUtf8 = clientSecret.toByteArray(StandardCharsets.UTF_8)

        val signatureBytes = calculateHmacSha512(signatureUtf8, secretUtf8)

        return android.util.Base64.encodeToString(signatureBytes, android.util.Base64.NO_WRAP)
    }

    private fun calculateSHA256(data: String): String {
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(data.toByteArray(StandardCharsets.UTF_8))
        return hashBytes.joinToString("") { "%02x".format(it) }
    }

    private fun calculateHmacSha512(data: ByteArray, key: ByteArray): ByteArray {
        val hmacSha512 = Mac.getInstance("HmacSHA512")
        val secretKeySpec = SecretKeySpec(key, "HmacSHA512")
        hmacSha512.init(secretKeySpec)
        return hmacSha512.doFinal(data)
    }

    fun decryptInputKey(cipherText: String, secretKey: String): String {
        try {
            val keyBytes = secretKey.toByteArray(StandardCharsets.UTF_8)
            val keySpec = SecretKeySpec(keyBytes, "AES")
            val cipher = Cipher.getInstance("AES/ECB/PKCS5Padding")
            cipher.init(Cipher.DECRYPT_MODE, keySpec)
            val encryptedBytes = android.util.Base64.decode(cipherText, android.util.Base64.NO_WRAP)

            val decryptedBytes = cipher.doFinal(encryptedBytes)
            return String(decryptedBytes, StandardCharsets.UTF_8)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }
}