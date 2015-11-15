package aries.maven;

import junit.framework.TestCase;

import org.eclipse.aether.repository.RemoteRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


public class QueryRepositoryTest extends TestCase {


	@Before
	public void setUp() throws Exception {
		super.setUp();
	}

	@After
	public void tearDown() throws Exception {
		super.tearDown();
	}

	@Test
	public void testQuery() throws Exception {
//		Reader myreader = new FileReader("pom.xml");
//		MavenXpp3Reader reader = new MavenXpp3Reader();
//		Model model = reader.read(myreader);
//		MavenProject project = new MavenProject(model);
//		List<Dependency> dependencies = project.getDependencies();
//
//		String mavenHome = System.getenv("M2_HOME");
//		if (mavenHome == null)
//			mavenHome = "/workspace/.m2";
//		ArtifactRepository localRepository = new MavenArtifactRepository(
//				"local",
//				new File(mavenHome + "/repository").toURI().toURL().toString(),
//				new DefaultRepositoryLayout(),
//				new ArtifactRepositoryPolicy(),
//				new ArtifactRepositoryPolicy());
//
//		//List<Dependency> depmgtdeps = project.getDependencyManagement().getDependencies();
//
//		//Artifact artifact = new DefaultArtifact();
//
//		RemoteRepository central = new RemoteRepository("central", "default", "file://workspace/.m2");
//		RepositorySystem repositorySystem = new LegacyRepositorySystem();
//		ArtifactResolutionRequest request = new ArtifactResolutionRequest();
//		//request.setArtifact(artifact);
//		repositorySystem.resolve(request);
//
//		CollectRequest collectRequest = new CollectRequest();
//		//collectRequest.setRoot( dependency );
//		collectRequest.addRepository( central );
//
//		System.out.println();

	}

//	public static RepositorySystem newRepositorySystem()
//	{
//		/*
//		 * Aether's components implement org.sonatype.aether.spi.locator.Service to ease manual wiring and using the
//		 * prepopulated DefaultServiceLocator, we only need to register the repository connector factories.
//		 */
//		DefaultServiceLocator locator = new DefaultServiceLocator();
//		locator.addService( RepositoryConnectorFactory.class, FileRepositoryConnectorFactory.class );
//		//locator.addService( RepositoryConnectorFactory.class, WagonRepositoryConnectorFactory.class );
//		locator.setServices( WagonProvider.class, new ManualWagonProvider() );
//
//		return locator.getService( RepositorySystem.class );
//	}

//	public static RepositorySystemSession newRepositorySystemSession( RepositorySystem system )
//	{
//		MavenRepositorySystemSession session = new MavenRepositorySystemSession();
//
//		LocalRepository localRepo = new LocalRepository( "target/local-repo" );
//		session.setLocalRepositoryManager( system.newLocalRepositoryManager( localRepo ) );
//
//		session.setTransferListener( new ConsoleTransferListener() );
//		session.setRepositoryListener( new ConsoleRepositoryListener() );
//
//		// uncomment to generate dirty trees
//		// session.setDependencyGraphTransformer( null );
//
//		return session;
//	}

	public static RemoteRepository newCentralRepository() {
		RemoteRepository.Builder builder = new RemoteRepository.Builder("central", "default", "http://repo1.maven.org/maven2/");
		return builder.build();
	}


	//        RepositorySystem repoSystem = newRepositorySystem();
	//
	//        RepositorySystemSession session = newSession( repoSystem );
	//
	//        Dependency dependency =
	//            new Dependency( new DefaultArtifact( "org.apache.maven:maven-profile:2.2.1" ), "compile" );
	//        RemoteRepository central = new RemoteRepository( "central", "default", "http://repo1.maven.org/maven2/" );
	//
	//        CollectRequest collectRequest = new CollectRequest();
	//        collectRequest.setRoot( dependency );
	//        collectRequest.addRepository( central );
	//        DependencyNode node = repoSystem.collectDependencies( session, collectRequest ).getRoot();
	//
	//        DependencyRequest dependencyRequest = new DependencyRequest( node, null );
	//
	//        repoSystem.resolveDependencies( session, dependencyRequest  );
	//
	//        PreorderNodeListGenerator nlg = new PreorderNodeListGenerator();
	//        node.accept( nlg );
	//        System.out.println( nlg.getClassPath() );

	//	}

	//    private static RepositorySystem newRepositorySystem()
	//    {
	//        DefaultServiceLocator locator = new DefaultServiceLocator();
	//        locator.setServices( WagonProvider.class, new ManualWagonProvider() );
	//        locator.addService( RepositoryConnectorFactory.class, WagonRepositoryConnectorFactory.class );
	//
	//        return locator.getService( RepositorySystem.class );
	//    }
	//    
	//    private static RepositorySystemSession newSession( RepositorySystem system )
	//    {
	//        MavenRepositorySystemSession session = new MavenRepositorySystemSession();
	//
	//        LocalRepository localRepo = new LocalRepository( "target/local-repo" );
	//        session.setLocalRepositoryManager( system.newLocalRepositoryManager( localRepo ) );
	//
	//        return session;
	//    }


}
