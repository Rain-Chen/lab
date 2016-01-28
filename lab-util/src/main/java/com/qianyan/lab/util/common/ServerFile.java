package com.qianyan.lab.util.common;

import com.qianyan.lab.util.constant.LabUtilErrorMessageConst;
import com.qianyan.lab.util.exception.SystemException;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

/**
 * Created with Intellij IDEA.
 * Created by rain chen
 * User: rain chen
 * Date: 16-1-28
 * Time: 下午11:11.
 */
public final class ServerFile {

    //默认文件存放路径
    private static final String DEFALUT_UPLOAD_FILE_FOLDER = StringUtil.formatFilePath("webroot:upload");
    //临时文件存放路径
    private static String UPLOAD_FILE_FOLDER = null;

    //单利
    private ServerFile() {

    }

    /**
     * 设置服务器文件存放路径
     *
     * @param serverFileFolder 服务器文件存放路径
     */
    public static void setServerFileFolder(String serverFileFolder) {
        UPLOAD_FILE_FOLDER = StringUtil.formatFilePath(serverFileFolder);
        File file = new File(UPLOAD_FILE_FOLDER);
        if (!file.exists()) file.mkdirs();
    }

    /**
     * 获得服务器上传文件路径
     *
     * @return 若上传路径不为空则返回，若为空则返回默认路径/upload
     */
    public static String getServiceFileFolder() {
        if (UPLOAD_FILE_FOLDER == null) {
            return DEFALUT_UPLOAD_FILE_FOLDER;
        } else {
            return UPLOAD_FILE_FOLDER;
        }
    }

    /**
     * 在服务器上创建一个新文件
     *
     * @param fileName 文件名
     * @return 文件对象
     */
    public static File createFile(String fileName) {
        String fullFilePath = StringUtil.formatFilePath(getServiceFileFolder() + fileName);
        int lastSepIdx = fullFilePath.lastIndexOf(File.separator);
        if (lastSepIdx > 0) {
            String tempDir = fullFilePath.substring(0, lastSepIdx);
            File dir = new File(tempDir);
            if (!dir.exists()) {
                boolean mkdirs = dir.mkdirs();
            }
        }
        File file = new File(fullFilePath);
        if (!file.exists()) {
            try {
                boolean newFile = file.createNewFile();
            } catch (IOException e) {
                throw new SystemException(LabUtilErrorMessageConst.MODULE_NAME,
                        e, LabUtilErrorMessageConst.ERROR_MESSAGE_200006, fileName);
            }
        }
        return file;
    }

    /**
     * 在服务器子文件夹下创建一个新文件
     *
     * @param subPath  相对根目录的子目录
     * @param fileName 文件名
     * @return 文件对象
     */
    public static File createFile(String subPath, String fileName) {
        String dirPath = getServiceFileFolder() + subPath;
        File dir = new File(dirPath);
        if (!dir.exists()) {
            boolean mkdirs = dir.mkdirs();
        }
        File file = new File(dirPath + fileName);
        if (!file.exists()) {
            try {
                boolean newFile = file.createNewFile();
            } catch (IOException e) {
                throw new SystemException(LabUtilErrorMessageConst.MODULE_NAME,
                        e, LabUtilErrorMessageConst.ERROR_MESSAGE_200006, fileName);
            }
        }
        return file;
    }

    /**
     * 获取服务器上的一个文件
     *
     * @param fileName 文件名
     * @return 文件对象
     */
    public static File getFile(String fileName) {
        return new File(getServiceFileFolder() + fileName);
    }

    /**
     * 生成随机文件名称使用UUID生成(包含"-")
     *
     * @param fileType 文件后缀
     * @return 文件名称
     */
    public static String generateFileName(String fileType) {
        return UUID.randomUUID().toString() + fileType;
    }

    /**
     * 生成随机文件名称使用UUID生成(不包含"-")
     *
     * @param fileType 文件后缀
     * @return 文件名称
     */
    public static String generateFileNameWithoutM(String fileType) {
        String s = UUID.randomUUID().toString();
        return s.replaceAll("-", "");
    }
}
