package net.timcarpenter.maven.plugin.bpel;

import java.io.File;

import org.apache.maven.plugin.Mojo;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugin.testing.AbstractMojoTestCase;

public class TestBpelCompilerMojo extends AbstractMojoTestCase {
    private static final String POM_PATH = getBasedir() + "/src/test/resources/";
    private static final String DRY_RUN_COMPILABLE_POM = POM_PATH + "test-dry-run-pom.xml";
    private static final String COMPILABLE_WITH_OUTPUT_POM = POM_PATH + "test-output-pom.xml";
    private static final String UNCOMPILABLE_POM = POM_PATH + "test-error-pom.xml";
    
    /**
     * Test the creation of the mojo.
     */
    @SuppressWarnings("unused")
    public void testMojoCreation() throws Exception {
        Mojo mojo = lookupMojo("compile", new File(DRY_RUN_COMPILABLE_POM));
        BpelCompilerMojo compiler = (BpelCompilerMojo) mojo;
    }
    
    /**
     * Test dry run compilation.
     */
    public void testCompilationSuccess() throws Exception {
        Mojo mojo = lookupMojo("compile", new File(DRY_RUN_COMPILABLE_POM));
        BpelCompilerMojo compiler = (BpelCompilerMojo) mojo;
        try {
            compiler.execute();
        } catch (MojoFailureException me) {
            me.printStackTrace();
            fail("Compilation should have succeeded.");
        }
        
        File outputFile = new File(getBasedir(), "/src/test/resources/bpel/compilable/sample.cbp");
        assertFalse("Output file should not exist.", outputFile.exists());
    }
    
    /**
     * Test compilation with output.
     */
    public void testCompilationSuccessWithOutput() throws Exception {
        Mojo mojo = lookupMojo("compile", new File(COMPILABLE_WITH_OUTPUT_POM));
        BpelCompilerMojo compiler = (BpelCompilerMojo) mojo;
        try {
            compiler.execute();
        } catch (MojoFailureException me) {
            me.printStackTrace();
            fail("Compilation should have succeeded.");
        }
        
        File outputFile = new File(getBasedir(), "/src/test/resources/bpel/compilable/sample.cbp");
        assertTrue("Output file should exist", outputFile.exists());
        outputFile.deleteOnExit();
    }
    
    /**
     * Test compilation failures
     */
    public void testCompilationFailure() throws Exception {
        Mojo mojo = lookupMojo("compile", new File(UNCOMPILABLE_POM));
        BpelCompilerMojo compiler = (BpelCompilerMojo) mojo;
        try {
            compiler.execute();
            fail("Compilation should have failed the build.");
        } catch (MojoFailureException me) {
            // Expected
        }
    }

}
