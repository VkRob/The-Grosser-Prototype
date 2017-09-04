package engine.script;

import java.util.ArrayList;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.JsePlatform;

public class Script {

	public static ArrayList<Object> getNewArrayList() {
		return new ArrayList<Object>();
	}

	private static Logger LOG = LogManager.getLogger(Script.class);
	private static final String scriptFolder = "./script/";

	private LuaValue luaGlobals;
	private String filename;

	public Script(String filename) {
		this.filename = filename;
		luaGlobals = JsePlatform.standardGlobals();
		luaGlobals.get("dofile").call(LuaValue.valueOf(scriptFolder + filename));
	}

	public Script(Script script) {
		this.luaGlobals = script.luaGlobals;
	}

	public void refresh() {
		LuaValue backupGlobals = luaGlobals;
		LuaValue nextGlobals;
		try {
			nextGlobals = JsePlatform.standardGlobals();
			nextGlobals.get("dofile").call(LuaValue.valueOf(scriptFolder + filename));
		} catch (Exception e) {
			nextGlobals = backupGlobals;
		}
		luaGlobals = nextGlobals;
	}

	private LuaValue loadToLua(Object o) {
		return CoerceJavaToLua.coerce(o);
	}

	public void execute(String functionName, Object arg) {
		LuaValue luaFunction = luaGlobals.get(functionName);
		if (!luaFunction.isnil()) {
			luaFunction.call(loadToLua(arg));
		} else {
			LOG.error("Lua Function not found");
		}
	}

	public void execute(String functionName) {
		LuaValue luaFunction = luaGlobals.get(functionName);
		if (!luaFunction.isnil()) {
			luaFunction.call();
		} else {
			LOG.error("Lua Function not found");
		}
	}

	public void execute(String functionName, Object arg, Object arg2) {
		LuaValue luaFunction = luaGlobals.get(functionName);
		if (!luaFunction.isnil()) {
			luaFunction.call(loadToLua(arg), loadToLua(arg2));
		} else {
			LOG.error("Lua Function not found");
		}
	}
}