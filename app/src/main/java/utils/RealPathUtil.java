package utils;

import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.provider.DocumentsContract;
import android.provider.MediaStore;

import com.example.clothingstore.UpdateProfileActivity;

public class RealPathUtil {

    public static String getRealPath(UpdateProfileActivity context, Uri uri) {
        String realPath = null;
        // Kiểm tra API level để sử dụng cách lấy đường dẫn phù hợp
        if (Build.VERSION.SDK_INT < 11) {
            realPath = getRealPathFromUriBelowApi11((Context) context, uri);
        } else if (Build.VERSION.SDK_INT < 19) {
            realPath = getRealPathFromUriApi11To18((Context) context, uri);
        } else {
            realPath = getRealPathFromUriAboveApi19((Context) context, uri);
        }
        return realPath;
    }
    private static String getRealPathFromUriBelowApi11(Context context, Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String realPath = cursor.getString(columnIndex);
            cursor.close();
            return realPath;
        }
        return null;
    }

    private static String getRealPathFromUriApi11To18(Context context, Uri uri) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = context.getContentResolver().query(uri, projection, null, null, null);
        if (cursor != null) {
            int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
            cursor.moveToFirst();
            String realPath = cursor.getString(columnIndex);
            cursor.close();
            return realPath;
        }
        return null;
    }

    private static String getRealPathFromUriAboveApi19(Context context, Uri uri) {
        if (uri == null) {
            return null;
        }
        String realPath = null;
        if (DocumentsContract.isDocumentUri(context, uri)) {
            // Nếu là một tài liệu được lưu trữ trên dịch vụ DocumentsProvider
            String documentId = DocumentsContract.getDocumentId(uri);
            if ("com.android.providers.media.documents".equals(uri.getAuthority())) {
                String id = documentId.split(":")[1];
                String selection = MediaStore.Images.Media._ID + "=?";
                String[] selectionArgs = { id };
                String[] projection = { MediaStore.Images.Media.DATA };
                Cursor cursor = context.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                        projection, selection, selectionArgs, null);
                if (cursor != null) {
                    int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                    cursor.moveToFirst();
                    realPath = cursor.getString(columnIndex);
                    cursor.close();
                }
            } else if ("com.android.providers.downloads.documents".equals(uri.getAuthority())) {
                Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(documentId));
                realPath = getDataColumn(context, contentUri, null, null);
            }
        } else if ("content".equalsIgnoreCase(uri.getScheme())) {
            // Nếu là một content URI
            realPath = getDataColumn(context, uri, null, null);
        } else if ("file".equalsIgnoreCase(uri.getScheme())) {
            // Nếu là một file URI
            realPath = uri.getPath();
        }
        return realPath;
    }

    private static String getDataColumn(Context context, Uri uri, String selection, String[] selectionArgs) {
        String[] projection = { MediaStore.Images.Media.DATA };
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs, null);
            if (cursor != null && cursor.moveToFirst()) {
                int columnIndex = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                return cursor.getString(columnIndex);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


}
