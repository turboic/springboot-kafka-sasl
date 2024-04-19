package com.example.cloud.utils;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Base64;

public class ImageUtils {
    public static String image2Base64(InputStream is) {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream();) {
            byte[] result;
            byte[] buf = new byte[1024];
            int len;
            while (true) {
                try {
                    if (!((len = is.read(buf)) > 0)) {
                        break;
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                out.write(buf, 0, len);
            }
            result = out.toByteArray();
            return Base64.getEncoder().encodeToString(result);
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(is);
        }
    }

    public static String image2Base64(File file) {
        try {
            return image2Base64(FileUtils.openInputStream(file));
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String image2Base64(String sourceJpg) {
        return image2Base64(new File(sourceJpg));
    }


    public static String base642Image(String targetJpg, String str) {
        try {
            FileUtils.writeByteArrayToFile(new File(targetJpg), Base64.getDecoder().decode(str));
            return targetJpg;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
