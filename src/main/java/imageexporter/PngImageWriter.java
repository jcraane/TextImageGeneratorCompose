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

import javax.imageio.ImageIO;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

/**
 * Implementation of the ImageWriter which handles png exports.
 * User: Jamie Craane
 */
public final class PngImageWriter {
    private static final String PNG = "png";

    /**
     * {@inheritDoc}
     * @param image The image to write to the outputstream.
     * @param outputStream The outputStream to write the image to.
     */
    public void writeImageToOutputStream(final TextImageCanvas image, final OutputStream outputStream) throws IOException {
        ImageIO.write(image.getImage(), PNG, outputStream);
    }

    /**
     * {@inheritDoc}
     * @param image The image to write to the file.
     * @param file The outputStream to write the image to.
     */
    public void writeImageToFile(final TextImageCanvas image, final File file) throws IOException {
        OutputStream os = new FileOutputStream(file);
        try {
            ImageIO.write(image.getImage(), PNG, os);
        } finally {
            os.close();
        }
    }
}
