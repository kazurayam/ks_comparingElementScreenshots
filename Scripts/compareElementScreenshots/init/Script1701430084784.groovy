import java.nio.file.Paths

import org.apache.commons.io.FileUtils

import internal.GlobalVariable

FileUtils.deleteDirectory(Paths.get(GlobalVariable.OUTPUT_DIR_compareElementScreenshots).toFile())
