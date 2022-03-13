package zad1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.Charset;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class Futil {
	public static void processDir(String dirName, String resultFileName) {
		try {
			Files.walkFileTree(Paths.get(dirName), new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					if (file.toString().trim().endsWith(".txt")) {
						File readFile = file.toFile();
						try (FileInputStream fileInputStream = new FileInputStream(readFile)) {
							FileChannel fileChannel = fileInputStream.getChannel();
							int fileSize = (int) fileChannel.size();

							ByteBuffer byteBuffer = ByteBuffer.allocate(fileSize);
							fileChannel.read(byteBuffer);
							fileChannel.close();

							Append(DecodeToUTF8(byteBuffer), resultFileName);
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}
					}

					return FileVisitResult.CONTINUE;
				}

				@Override
				public FileVisitResult postVisitDirectory(Path dir, IOException e) throws IOException {
					if (e == null) {
						return FileVisitResult.CONTINUE;
					} else {
						throw e;
					}
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static ByteBuffer DecodeToUTF8(ByteBuffer byteBuffer) {
		byteBuffer.rewind();
		CharBuffer charBufferCP = Charset.forName("Cp1250").decode(byteBuffer);

		return Charset.forName("UTF-8").encode(charBufferCP.toString());
	}

	private static void Append(ByteBuffer byteBuffer, String resultFileName) {
		FileOutputStream fileOutputStream;
		try {
			fileOutputStream = new FileOutputStream(resultFileName, true);
			FileChannel fileChannel = fileOutputStream.getChannel();

			fileChannel.write(byteBuffer);
			fileChannel.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
