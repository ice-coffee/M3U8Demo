package com.mzp.player;

import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 作者:王浩 邮件:bingoogolapple@gmail.com
 * 创建时间:16/12/16 上午11:59
 * 描述:
 */
class StorageUtil {

    private StorageUtil() {
    }

    /**
     * 保存 apk 文件
     *
     * @param is
     * @param saveFile
     * @return
     */
    static File saveFile(InputStream is, File saveFile) {

        if (writeFile(saveFile, is)) {
            return saveFile;
        } else {
            return null;
        }
    }

    /**
     * 根据输入流，保存文件
     *
     * @param saveFile 下载目录
     * @param is
     * @return
     */
    static boolean writeFile(File saveFile, InputStream is) {
        OutputStream os = null;
        try {
            os = new FileOutputStream(saveFile);
            byte data[] = new byte[1024];
            int length = -1;
            while ((length = is.read(data)) != -1) {
                os.write(data, 0, length);
            }
            os.flush();
            return true;
        } catch (Exception e) {
            if (saveFile != null && saveFile.exists()) {
                saveFile.deleteOnExit();
            }
            e.printStackTrace();
        } finally {
            closeStream(os);
            closeStream(is);
        }
        return false;
    }

    /**
     * 删除文件或文件夹
     *
     * @param file
     */
    static void deleteFile(File file) {
        try {
            if (file == null || !file.exists()) {
                return;
            }

            if (file.isDirectory()) {
                File[] files = file.listFiles();
                if (files != null && files.length > 0) {
                    for (File f : files) {
                        if (f.exists()) {
                            if (f.isDirectory()) {
                                deleteFile(f);
                            } else {
                                f.delete();
                            }
                        }
                    }
                }
            } else {
                file.delete();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 关闭流
     *
     * @param closeable
     */
    static void closeStream(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
