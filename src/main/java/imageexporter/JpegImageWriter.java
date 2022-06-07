/*
 * Copyright 2014 Jamie Craane - Capax IT
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package imageexporter;

import dev.jamiecraane.generator.core.TextImageCanvas;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.plugins.jpeg.JPEGImageWriteParam;
import javax.imageio.stream.ImageOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Iterator;
import java.util.Locale;

/**
 * Implementation of the ImageWriter that handlers Jpeg export.
 * Uses the highest quality Jpeg.
 * <p>
 * User: Jamie Craane
 */
public final class JpegImageWriter {
    private static final int MAX_COMPRESSION_QUALITY = 1;

    /**
     * {@inheritDoc}
     *
     * @param image        The image to write to the outputstream.
     * @param outputStream The outputStream to write the image to.
     * @throws IOException
     */
    public void writeImageToOutputStream(final TextImageCanvas image, final OutputStream outputStream) throws IOException {
        compressJpegStream(outputStream, image);
    }

    /**
     * {@inheritDoc}
     *
     * @param image The image to write to the file.
     * @param file  The outputStream to write the image to.
     * @throws IOException
     */
    public void writeImageToFile(final TextImageCanvas image, final File file) throws IOException {
        OutputStream outputStream = new FileOutputStream(file);
        compressJpegStream(outputStream, image);
    }

    private void compressJpegStream(
            final OutputStream outputStream,
            final TextImageCanvas image) throws IOException {
        javax.imageio.ImageWriter jpegWriter = getJpegWriter();
        ImageWriteParam imageWriteParam = getHighestCompressionQualityParams();
        writeImageToOutputStream(outputStream, image, jpegWriter, imageWriteParam);

    }

    private javax.imageio.ImageWriter getJpegWriter() {
        javax.imageio.ImageWriter writer = null;
        Iterator<javax.imageio.ImageWriter> iter = ImageIO.getImageWritersByFormatName("jpg");
        if (iter.hasNext()) {
            writer = iter.next();
        }
        return writer;
    }

    private ImageWriteParam getHighestCompressionQualityParams() {
        // Set the compression quality
        ImageWriteParam iwparam = new JPEGImageWriteParam(Locale.getDefault()) {
            public void setCompressionQuality(float quality) {
                this.compressionQuality = quality;
            }
        };
        iwparam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        iwparam.setCompressionQuality(MAX_COMPRESSION_QUALITY);
        return iwparam;
    }

    private void writeImageToOutputStream(
            final OutputStream outputStream,
            final TextImageCanvas image,
            final javax.imageio.ImageWriter jpegWriter,
            final ImageWriteParam imageWriteParam) throws IOException {
        ImageOutputStream ios = ImageIO.createImageOutputStream(outputStream);
        try {
            jpegWriter.setOutput(ios);
            jpegWriter.write(null, new IIOImage((image.getImage()), null, null), imageWriteParam);
        } finally {
            cleanUp(jpegWriter, ios);
        }
    }

    private void cleanUp(final javax.imageio.ImageWriter jpegWriter, final ImageOutputStream ios) throws IOException {
        ios.flush();
        jpegWriter.dispose();
        ios.close();
    }
}
