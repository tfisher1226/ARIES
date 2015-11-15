/*
   Copyright 2010 Tim Carpenter

   Licensed under the Apache License, Version 2.0 (the "License");
   you may not use this file except in compliance with the License.
   You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

   Unless required by applicable law or agreed to in writing, software
   distributed under the License is distributed on an "AS IS" BASIS,
   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
   See the License for the specific language governing permissions and
   limitations under the License.
*/

package net.timcarpenter.maven.plugin.bpel;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import org.apache.maven.model.FileSet;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.ode.bpel.compiler.BpelC;
import org.apache.ode.bpel.compiler.api.CompilationException;
import org.codehaus.plexus.util.FileUtils;

/**
 * @goal compile
 * @author Tim Carpenter
 */
public class BpelCompilerMojo extends AbstractMojo {
    /**
     * @parameter
     */
    private FileSet fileSet;
    
    /**
     * @parameter
     */
    private boolean dryRun;
    
    /**
     * Compiles BPEL
     * @execute
     */
    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        // Create a compiler.
        BpelC compiler = BpelC.newBpelCompiler();
        
        File dir = new File(fileSet.getDirectory());
        if (dir.isDirectory()) {
            getLog().info("Compiling BPEL from " + dir);
            
            String includes = getCommaSeparatedList(fileSet.getIncludes());
            String excludes = getCommaSeparatedList(fileSet.getExcludes());
            
            if (dryRun) {
                getLog().info("Dry run, not writing compilation output");
                // Don't write the output anywhere.
                compiler.setDryRun(true);
            }
            
            try {
                List<?> files = FileUtils.getFiles(dir, includes, excludes);
                for (Object f : files) {
                    File file = (File) f;
                    getLog().info("Compiling " + file.getAbsolutePath());
                    try {
                        compiler.compile(file, 0);
                    } catch (CompilationException ce) {
                        throw new MojoFailureException("Compilation of " + file.getAbsolutePath() + " failed.", ce);
                    }
                }
            } catch (IOException ioe) {
                throw new MojoFailureException("Failed to read BPEL files.", ioe);
            }
        } else {
            throw new MojoFailureException(dir + " is not a directory.");
        }
    }
    
    protected String getCommaSeparatedList(List<?> list) {
        StringBuffer buffer = new StringBuffer();
        for (Iterator<?> iterator = list.iterator(); iterator.hasNext();) {
            Object element = iterator.next();
            buffer.append(element.toString());
            if (iterator.hasNext()) {
                buffer.append(",");
            }
        }
        return buffer.toString();
    }
}

