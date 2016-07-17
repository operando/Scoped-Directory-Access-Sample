## Scoped Directory Access

* https://developer.android.com/preview/features/scoped-folder-access.html
* https://developer.android.com/reference/android/os/storage/StorageVolume.html
* http://willowtreeapps.com/blog/android-n-scoped-directory-access-overview/
* READ_EXTERNAL_STORAGE WRITE_EXTERNAL_STORAGEがなくても外部ストレージの特定のDirectoryにアクセスできるようになるAPIが提供される

## 実際に使ってる例

* https://github.com/kcwikizh/Akashi-Toolkit/blob/master/app/src/main/java/rikka/akashitoolkit/ui/ImageDisplayActivity.java

## memo

```
I/ActivityManager( 4573): START u0 {act=android.os.storage.action.OPEN_EXTERNAL_DIRECTORY cmp=com.android.documentsui/.OpenExternalDirectoryActivity (has extras)} from uid 10125 on display 0
```