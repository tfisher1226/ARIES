package org.codehaus.mojo.jboss;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file 
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, 
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY 
 * KIND, either express or implied.  See the License for the 
 * specific language governing permissions and limitations 
 * under the License.
 */

import org.apache.maven.plugin.MojoExecutionException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

/**
 * Hard deploys the file by copying it to the <code>$JBOSS_HOME/server/[serverName]/deploy</code> directory.
 * 
 * @author <a href="mailto:jgenender@apache.org">Jeff Genender</a>
 * @goal hard-deploy
 * @since 1.4
 */
public class HardDeployMojo
    extends AbstractJBossServerMojo
{

    /**
     * The names of the files or directories to deploy. If this is set, the fileName parameter will be ignored.
     * 
     * @parameter
     * @since 1.4.1
     */
    protected File[] fileNames;

    /**
     * The name of the file or directory to deploy or undeploy.
     * 
     * @parameter default-value="${project.build.directory}/${project.build.finalName}.${project.packaging}"
     */
    protected File fileName;

    /**
     * An optional name of a subdirectory on the deploy directory to be used
     * 
     * @parameter
     */
    protected String deploySubDir;

    /**
     * A boolean indicating if the artifact should be unpacked when deployed
     * 
     * @parameter default-value="false"
     */
    protected boolean unpack;

    /**
     * Main plugin execution.
     * 
     * @throws MojoExecutionException
     */
    public void execute()
        throws MojoExecutionException
    {

        checkConfig();

        if ( fileNames == null || fileNames.length == 0 )
        {
            fileNames = new File[1];
            fileNames[0] = fileName;
        }

        for ( int i = 0; i < fileNames.length; ++i )
        {
            try
            {
                String nextFileName = fileNames[i].getAbsolutePath();

                // Fix the ejb packaging to a jar
                String fixedFile = null;
                if ( nextFileName.toLowerCase().endsWith( "ejb" ) )
                {
                    fixedFile = nextFileName.substring( 0, nextFileName.length() - 3 ) + "jar";
                }
                else
                {
                    fixedFile = nextFileName;
                }

                String deployDir = deploySubDir == null ? "/deploy/" : ( "/deploy/" + deploySubDir + "/" );
                File src = new File( fixedFile );
                File dst = new File( jbossHome + "/server/" + serverName + deployDir + src.getName() );

                getLog().info(
                               ( unpack ? "Unpacking " : "Copying " ) + src.getAbsolutePath() + " to "
                                   + dst.getAbsolutePath() );
                copy( src, dst );
            }
            catch ( Exception e )
            {
                throw new MojoExecutionException( "Mojo error occurred: " + e.getMessage(), e );
            }
        }

    }

    private void copy( File srcDir, File dstDir )
        throws IOException
    {
        if ( srcDir.isDirectory() )
        {
            if ( !dstDir.exists() )
            {
                dstDir.mkdir();
            }

            String[] children = srcDir.list();
            for ( int i = 0; i < children.length; i++ )
            {
                copy( new File( srcDir, children[i] ), new File( dstDir, children[i] ) );
            }
        }
        else
        {
            copyFile( srcDir, dstDir );
        }
    }

    private void copyFile( File src, File dst )
        throws IOException
    {
        if ( unpack )
        {
            unpack( src, dst );
        }
        else
        {
            InputStream in = new FileInputStream( src );
            OutputStream out = new FileOutputStream( dst );

            streamcopy( in, out );

            in.close();
            out.close();
        }
    }

    private void streamcopy( InputStream in, OutputStream out )
        throws IOException
    {
        // Transfer bytes from in to out
        byte[] buf = new byte[1024];
        int len;
        while ( ( len = in.read( buf ) ) > 0 )
        {
            out.write( buf, 0, len );
        }
    }

    public void unpack( File zipFile, File targetDir )
        throws IOException
    {
        FileInputStream in = new FileInputStream( zipFile );
        ZipInputStream zipIn = new ZipInputStream( in );

        File dir = targetDir.getCanonicalFile();
        dir.mkdirs();
        ZipEntry entry;
        while ( ( entry = zipIn.getNextEntry() ) != null )
        {
            if ( entry.isDirectory() )
            {
                continue;
            }
            String file = targetDir + "/" + entry.getName();

            new File( file ).getParentFile().getCanonicalFile().mkdirs();

            FileOutputStream out = new FileOutputStream( file );
            streamcopy( zipIn, out );
            out.close();
        }
        zipIn.close();
    }
}
