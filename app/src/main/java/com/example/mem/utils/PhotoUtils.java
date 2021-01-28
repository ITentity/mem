package com.example.mem.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import android.text.TextUtils;

import com.example.mem.app.MyApplication;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * 获取图片的真实路径或者剪切图片等
 * Created by mk on 2018/4/27.
 */

public class PhotoUtils {
    private static final String TAG = "PhotoUtils";

    /**
     * @param activity    当前activity
     * @param imageUri    拍照后照片存储路径
     * @param requestCode 调用系统相机请求码
     */
    public static void takePicture(Activity activity, Uri imageUri, int requestCode) {
        //调用系统相机
        Intent intentCamera = new Intent();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intentCamera.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
        }
        intentCamera.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
        //将拍照结果保存至photo_file的Uri中，不保留在相册中
        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
        activity.startActivityForResult(intentCamera, requestCode);
    }

    /**
     * @param activity    当前activity
     * @param requestCode 打开相册的请求码
     */
    public static void openPic(Activity activity, int requestCode) {
        Intent photoPickerIntent = new Intent(Intent.ACTION_GET_CONTENT);
        photoPickerIntent.setType("image/*");
        activity.startActivityForResult(photoPickerIntent, requestCode);
    }

    /**
     * @param activity    当前activity
     * @param orgUri      剪裁原图的Uri
     * @param desUri      剪裁后的图片的Uri
     * @param aspectX     X方向的比例
     * @param aspectY     Y方向的比例
     * @param width       剪裁图片的宽度
     * @param height      剪裁图片高度
     * @param requestCode 剪裁图片的请求码
     */
    public static void cropImageUri(Activity activity, Uri orgUri, Uri desUri, int aspectX, int aspectY, int width, int height, int requestCode) {
        Intent intent = new Intent("com.android.camera.action.CROP");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        }
        intent.setDataAndType(orgUri, "image/*");
        intent.putExtra("crop", "true");
        intent.putExtra("aspectX", aspectX);
        intent.putExtra("aspectY", aspectY);
        intent.putExtra("outputX", width);
        intent.putExtra("outputY", height);
        intent.putExtra("scale", true);
        //将剪切的图片保存到目标Uri中
        intent.putExtra(MediaStore.EXTRA_OUTPUT, desUri);
        intent.putExtra("return-data", false);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
        intent.putExtra("noFaceDetection", true);
        activity.startActivityForResult(intent, requestCode);
    }

    /**
     * 读取uri所在的图片
     *
     * @param uri      图片对应的Uri
     * @param mContext 上下文对象
     * @return 获取图像的Bitmap
     */
    public static Bitmap getBitmapFromUri(Uri uri, Context mContext) {
        try {
            Bitmap bitmap = MediaStore.Images.Media.getBitmap(mContext.getContentResolver(), uri);
            return bitmap;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }



//    public static boolean copyImage(String oldPath, String newPath, int maxLen) {
//        if (TextUtils.isEmpty(oldPath) || TextUtils.isEmpty(oldPath)) {
//            return false;
//        }
//        File srcFile = new File(oldPath);
//        if (!srcFile.exists()) {
//            return false;
//        }
//        if (oldPath.equals(newPath) && new File(newPath).exists()) {
//            return true;
//        }
//        int[] wh = new int[]{960, 800};
//
//        if (maxLen > 0 && srcFile.length() / 1024 > maxLen) {
//            // 压缩复制
//            Bitmap bitmap = null;
//            bitmap = getImageThumbnail(srcFile.getAbsolutePath(), wh[0], wh[1]);
//            if (bitmap != null) {
//                try {
//                    compressAndCopyImage(bitmap, maxLen, newPath);
//                    return true;
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            }
//        } else {
//            // 复制
//            return copyFile(newPath, srcFile);
//        }
//        return false;
//    }

//    @SuppressLint("NewApi")
//    public static Bitmap getImageThumbnail(String imagePath, int width, int height) {
//        Bitmap bitmap = null;
//        BitmapFactory.Options options = new BitmapFactory.Options();
//        options.inJustDecodeBounds = true;
//        // 获取这个图片的宽和高，注意此处的bitmap为null
//        BitmapFactory.decodeFile(imagePath, options);
//        options.inSampleSize = calculateInSampleSize(options, width, height);
//        options.inJustDecodeBounds = false; // 设为 false
//        bitmap = getBitmapByPath(imagePath, options);
//
//        if (bitmap == null)
//            return null;
//        return bitmap;
//    }

    public static Bitmap getBitmapByPath(String filePath, BitmapFactory.Options opts) {
        Bitmap bitmap = null;
        InputStream in = null;
        try {
            File file = new File(filePath);
            in = new BufferedInputStream(new FileInputStream(file));
            bitmap = BitmapFactory.decodeStream(in, null, opts);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return bitmap;
    }

    public static void compressAndCopyImage(Bitmap image, long maxLen, String filePath) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        image.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        int options = 100;
        while (baos.toByteArray().length / 1024 > maxLen) {
            baos.reset();
            options -= 10;
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);
        }
        baos.reset();
        File file = new File(filePath.substring(0, filePath.lastIndexOf(File.separator)));
        if (!file.exists()) {
            file.mkdirs();
        }
        BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(filePath));
        image.compress(Bitmap.CompressFormat.JPEG, options, bos);
        bos.flush();
        bos.close();
        scanPhoto(filePath);
    }

    public static boolean copyFile(String newPath, File srcFile) {
        try {
            File dir = new File(newPath.substring(0, newPath.lastIndexOf("/")));
            if (!dir.exists())
                dir.mkdirs();
            File destFile = new File(newPath);
            if (!destFile.exists()) {
                destFile.createNewFile();
            }
            doCopyFile(srcFile, destFile);
            scanPhoto(newPath);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void doCopyFile(File srcFile, File destFile) throws IOException {
        if (destFile.exists() && destFile.isDirectory()) {
            throw new IOException("Destination '" + destFile + "' exists but is a directory");
        }

        FileInputStream input = new FileInputStream(srcFile);
        try {
            FileOutputStream output = new FileOutputStream(destFile);
            try {
                copyLarge(input, output);
            } finally {
                output.close();
            }
        } finally {
            input.close();
        }

        if (srcFile.length() != destFile.length()) {
            throw new IOException("Failed to copy full contents from '" + srcFile + "' to '" + destFile + "'");
        }
    }

    public static void copyLarge(InputStream input, OutputStream output) throws IOException {
        byte[] buffer = new byte[1024];
        int n = 0;
        while (-1 != (n = input.read(buffer))) {
            output.write(buffer, 0, n);
        }
    }

    public static String getEndType(String filePath) {
        try {
            String fName = getFileName(filePath);
            /* 取得扩展名 */
            String end = fName.substring(fName.lastIndexOf(".") + 1, fName.length()).toLowerCase();
            return end;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 根据文件绝对路径获取文件名
     *
     * @param filePath
     * @return
     */
    public static String getFileName(String filePath) {
        if (TextUtils.isEmpty(filePath))
            return "";
        return filePath.substring(filePath.lastIndexOf(File.separator) + 1);
    }

    /**
     * 让图库上能马上看到该图片
     */
    private static void scanPhoto(String imgFileName) {
        Intent mediaScanIntent = new Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE);
        File file = new File(imgFileName);
        Uri contentUri = Uri.fromFile(file);
        mediaScanIntent.setData(contentUri);
        MyApplication.getInstance().sendBroadcast(mediaScanIntent);
    }

    /**
     * @param context 上下文对象
     * @param uri     当前相册照片的Uri
     * @return 解析后的Uri对应的String
     */
    @SuppressLint("NewApi")
    public static String getPath(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
        String pathHead = "";
        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];
                if ("primary".equalsIgnoreCase(type)) {
                    return pathHead + Environment.getExternalStorageDirectory() + "/" + split[1];
                }
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);

                final Uri contentUri = ContentUris.withAppendedId(Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return pathHead + getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[]{split[1]};

                return pathHead + getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {
            return pathHead + getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return pathHead + uri.getPath();
        }
        return null;
    }

    /**
     * Get the value of the data column for this Uri. This is useful for
     * MediaStore Uris, and other file-based ContentProviders.
     *
     * @param context       The context.
     * @param uri           The Uri to query.
     * @param selection     (Optional) Filter used in the query.
     * @param selectionArgs (Optional) Selection arguments used in the query.
     * @return The value of the _data column, which is typically a file path.
     */
    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {column};
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                final int column_index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(column_index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    private static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    private static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    private static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }


    /**
     * 调用系统剪裁功能
     */
//    public static Intent cropPicture(String path) {
//        File file = new File(path);
//        if (!file.getParentFile().exists()) {
//            file.getParentFile().mkdirs();
//        }
//        /*裁剪前图片URI*/
//        Uri imageUri;
//        /*裁剪后图片URI*/
//        Uri outputUri;
//        Intent intent = new Intent("com.android.camera.action.CROP");
//        createFile(System.currentTimeMillis());
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            //添加这一句表示对目标应用临时授权该Uri所代表的文件
//            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//            //TODO:访问相册需要被限制，需要通过FileProvider创建一个content类型的Uri
//            imageUri = FileProvider.getUriForFile(MyApplication.getInstance(), Constant.FILE_CONTENT_FILEPROVIDER, file);
//            ContentValues contentValues = new ContentValues(1);
//            contentValues.put(MediaStore.Images.Media.DATA, tempFile.getAbsolutePath());
//            outputUri = MyApplication.getInstance().getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
//        } else {
//            imageUri = Uri.fromFile(file);
//            outputUri = Uri.fromFile(tempFile);
//        }
//        intent.setDataAndType(imageUri, "image/*");
//        intent.putExtra("crop", "true");
//        //设置宽高比例
//        intent.putExtra("aspectX", 1);
//        intent.putExtra("aspectY", 1);
//        //设置裁剪图片宽高
//        intent.putExtra("outputX", 300);
//        intent.putExtra("outputY", 300);
//        intent.putExtra("scale", true);
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputUri);
//        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());
//        intent.putExtra("noFaceDetection", true);
//        return intent;
//    }

    /**
     * 临时图片文件
     */
    private static File tempFile;

    /**
     * 创建图片文件
     *
     * @param time
     * @return
     */
    public static String createFile(long time) {
        String fileName = "";
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            String path = Environment.getExternalStorageDirectory() + File.separator + "decerp" + File.separator;
            File file = new File(path);
            if (!file.exists()) {
                file.mkdirs();
            }
            fileName = path + time + ".jpg";
            tempFile = new File(fileName);
        } else {
            ToastUtils.show("不存在sd卡");
        }
        return fileName;
    }


    /**
     * 获取图片uri
     *
     * @param path
     * @return
     */
//    public static Uri getImageUri(String path) {
//        Uri imageUri;
//        File file = new File(path);
//        if (!file.getParentFile().exists()) {
//            file.getParentFile().mkdirs();
//        }
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
//            imageUri = FileProvider.getUriForFile(MyApplication.getInstance(), Constant.FILE_CONTENT_FILEPROVIDER, file);
//        } else {
//            imageUri = Uri.fromFile(file);
//        }
//        return imageUri;
//    }
}
