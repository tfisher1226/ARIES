package aries.codegen;

import java.util.EnumSet;
import java.util.Set;


public class TestType {

	public static int Null = Flag.Null.value();
	public static int Execute = Flag.Execute.value();
	public static int Success = Flag.Success.value();
	public static int Commit = Flag.Commit.value();
	public static int Rollback = Flag.Rollback.value();
	public static int Exception = Flag.Exception.value();
	public static int Timeout = Flag.Timeout.value();
	public static int Cancel = Flag.Cancel.value();
	public static int Undo = Flag.Undo.value();
	public static int Cache = Flag.Cache.value();
	public static int Service = Flag.Service.value();
	public static int Manager = Flag.Manager.value();
	public static int Interface = Flag.Interface.value();
	public static int Repository = Flag.Repository.value();
	public static int UserTransaction = Flag.UserTransaction.value();
	public static int JTATransaction = Flag.JTATransaction.value();
	public static int GlobalTransaction = Flag.GlobalTransaction.value();

	public static TestType create(int values) {
		return new TestType(values);
	}

	public static String getName(int testType) {
		if (testType == Null) return "Null";
		if (testType == Execute) return "Execute";
		if (testType == Success) return "Success";
		if (testType == Commit) return "Commit";
		if (testType == Rollback) return "Rollback";
		if (testType == Exception) return "Exception";
		if (testType == Timeout) return "Timeout";
		if (testType == Cancel) return "Cancel";
		if (testType == Undo) return "Undo";
		if (testType == Cache) return "Cache";
		if (testType == Service) return "Service";
		if (testType == Manager) return "Manager";
		if (testType == Interface) return "Interface";
		if (testType == Repository) return "Repository";
		if (testType == UserTransaction) return "UserTransaction";
		if (testType == JTATransaction) return "JTATransaction";
		if (testType == GlobalTransaction) return "GlobalTransaction";
		return null;
	}

	
	private final Set<Flag> flags = EnumSet.noneOf(Flag.class);


	public TestType(int value) {
		for (Flag flag : Flag.values()) {
			if ((value & flag.value()) == 0)
				continue;
			flags.add(flag);
		}
	}

	public boolean isSet(Flag flag) {
		return flags.contains(flag);
	}

	public boolean isNull() { return flags.contains(Flag.Null); }
	public boolean isExecute() { return flags.contains(Flag.Execute); }
	public boolean isSuccess() { return flags.contains(Flag.Success); }
	public boolean isCommit() { return flags.contains(Flag.Commit); }
	public boolean isRollback() { return flags.contains(Flag.Rollback); }
	public boolean isException() { return flags.contains(Flag.Exception); }
	public boolean isTimeout() { return flags.contains(Flag.Timeout); }
	public boolean isCancel() { return flags.contains(Flag.Cancel); }
	public boolean isUndo() { return flags.contains(Flag.Undo); }
	public boolean isCache() { return flags.contains(Flag.Cache); }
	public boolean isService() { return flags.contains(Flag.Service); }
	public boolean isManager() { return flags.contains(Flag.Manager); }
	public boolean isInterface() { return flags.contains(Flag.Interface); }
	public boolean isRepository() { return flags.contains(Flag.Repository); }
	public boolean isUserTransaction() { return flags.contains(Flag.UserTransaction); }
	public boolean isJTATransaction() { return flags.contains(Flag.JTATransaction); }
	public boolean isGlobalTransaction() { return flags.contains(Flag.GlobalTransaction); }
	public boolean isTransactional() { return isCommit() || isRollback(); }

	public String getName() {
		StringBuffer buf = new StringBuffer();
		if (isCache()) append(buf, "cache");
		if (isService()) append(buf, "service");
		if (isManager()) append(buf, "manager");
		if (isInterface()) append(buf, "interface");
		if (isRepository()) append(buf, "repository");
		if (isUserTransaction()) append(buf, "utx");
		if (isJTATransaction()) append(buf, "tx");
		if (isGlobalTransaction()) append(buf, "GlobalTransaction");
		if (isExecute()) append(buf, "execute");
		if (isSuccess()) append(buf, "success");
		if (isException()) append(buf, "exception");
		if (isTimeout()) append(buf, "timeout");
		if (isCommit()) append(buf, "commit");
		if (isRollback()) append(buf, "rollback");
		if (isCancel()) append(buf, "cancel");
		if (isUndo()) append(buf, "undo");
		if (isNull()) append(buf, "null");
//		if (isService() && isException()) append(buf, "ServiceException");
//		if (isManager() && isException()) append(buf, "ManagerException");
//		if (isCache() && isException()) append(buf, "CacheException");
		return buf.toString();
	}

	private void append(StringBuffer buf, String text) {
		if (buf.length() > 0)
			buf.append("_");
		buf.append(text);
	}


	public enum Flag {
		Null(1),
		Execute(2),
		Success(4),
		Commit(8),
		Rollback(16),
		Exception(32),
		Timeout(64),
		Cancel(128),
		Undo(256),
		Cache(512),
		Service(1024),
		Manager(2048),
		Interface(4096),
		Repository(8192),
		UserTransaction(16384),
		JTATransaction(32768),
		GlobalTransaction(65536);

		private int value;


		private Flag(int value) {
			this.value = value;
		}

		public int value() {
			return value;
		}
	}

}
