package admin.dao;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;


@RunWith(Suite.class)
@Suite.SuiteClasses({
	PermissionDaoIT.class,
	PreferencesDaoIT.class,
	RoleDaoIT.class,
	SkinDaoIT.class,
	UserDaoIT.class
})
public class AdminDaoITSuite {
}
