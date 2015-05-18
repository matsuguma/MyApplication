package com.example.matsuguma.myapplication;

/**
 * Created by matsuguma on 2015/05/13.
 */
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
/**
 * FlushedInputStream
 * Bitmapデコード時に"decoder->decode returned false"のエラーが発生する対策用のFilterInputStream
 * http://code.google.com/p/android/issues/detail?id=6066
 */
public class FlushedInputStream extends FilterInputStream {
    public FlushedInputStream(InputStream inputStream) {
        super(inputStream);
    }
    public long skip(long n) throws IOException {
        long totalBytesSkipped = 0L;
        while (totalBytesSkipped < n) {
            long bytesSkipped = in.skip(n - totalBytesSkipped);
            if (bytesSkipped == 0L) {
                int Byte = read();
                if (Byte < 0) {
                    break;  // we reached EOF
                } else {
                    bytesSkipped = 1; // we read one byte
                }
            }
            totalBytesSkipped += bytesSkipped;
        }
        return totalBytesSkipped;
    }
}