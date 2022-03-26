package zad1;

import java.io.FileNotFoundException;
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
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.BasicFileAttributes;

public class Futil {
	public static void processDir(String dirName, String resultFileName) {
		try {
			Files.walkFileTree(Paths.get(dirName), new SimpleFileVisitor<Path>() {
				@Override
				public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
					if (file.toString().trim().endsWith(".txt")) {
						ByteBuffer byteBuffer = null;
						
						try (FileChannel fileInputStream = FileChannel.open(file)) {
							int fileSize = (int) fileInputStream.size();

							byteBuffer = ByteBuffer.allocate(fileSize);
							fileInputStream.read(byteBuffer);
							
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
		try {
			FileChannel fileOutputStream = FileChannel.open(Path.of(resultFileName), StandardOpenOption.CREATE, StandardOpenOption.APPEND);

			fileOutputStream.write(byteBuffer);
			fileOutputStream.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
