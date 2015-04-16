package org.grooscript.util

import static org.grooscript.util.Util.SEP
import static org.grooscript.util.Util.GROOVY_EXTENSION
/**
 * Created by jorgefrancoleza on 10/3/15.
 */
class FileSolver {

    boolean exists(String pathFile) {
        def file = new File(pathFile)
        file && file.exists() && file.file
    }

    String readFile(String pathFile) {
        exists(pathFile) ? new File(pathFile).text : null
    }

    String canonicalPath(String pathFile) {
        new File(pathFile).canonicalPath
    }

    String filePathFromClassName(String className, String classPath) {
        def begin = classPath ? classPath + SEP : ''
        begin + className.replaceAll(/\./, SEP) + GROOVY_EXTENSION
    }

    void saveFile(String filePath, String content) {
        File file = new File(filePath)
        file.getParentFile().mkdirs()
        file.text = content
    }

    boolean isFolder(String pathFolder) {
        File file = new File(pathFolder)
        file && file.exists() && file.directory
    }
}
