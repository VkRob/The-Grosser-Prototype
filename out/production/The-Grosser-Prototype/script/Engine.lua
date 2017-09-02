-- This file functions similar to a header file in C.
-- It links methods and classes in the Java parts of the program to
-- Lua objects and methods for easy reference.

--Scripting Memory Management
function getVar(my_scene, id)
  return my_scene:getScriptVars():get(id);
end
function addVar(my_scene, obj)
  my_scene:getScriptVars():add(obj);
  return my_scene:getScriptVars():size()-1;
end
function createVar(my_scene, obj)
  return getVar(my_scene, addVar(my_scene, obj));
end

--Java Native Lib
function new_ArrayList()
  return luajava.bindClass("engine.script.Script"):getNewArrayList();
end

--Scene
Scene_Manager = luajava.bindClass("engine.logic.SceneManager");
scene_manager = luajava.bindClass("engine.Engine").sceneManager;
Scene = luajava.bindClass("engine.logic.Scene");
current_scene = luajava.bindClass("engine.Engine").sceneManager:getCurrentScene();
camera = luajava.bindClass("engine.Engine").sceneManager:getCurrentScene():getCamera();

--Math
function new_Vector2f(x,y)
  return luajava.newInstance("org.joml.Vector2f", x, y);
end
function new_Vector3f(x,y,z)
  return luajava.newInstance("org.joml.Vector3f", x, y, z);
end
function round(num)
  return math.floor(num + 0.5);
end

--Entity
Entity = luajava.bindClass("engine.entity.Entity");
function new_EntitySprite()
  return luajava.newInstance("engine.entity.EntitySprite");
end
function new_TileEntityXY(x, y, id)
  return luajava.newInstance("engine.entity.TileEntity", new_Vector2f(x, y), id);
end
function new_TileEntity(pos, id)
  return luajava.newInstance("engine.entity.TileEntity", pos, id);
end

--Tile
Tile = luajava.bindClass("engine.tile.Tile");

--Input
Input = {
  Class = luajava.bindClass("engine.input.Input"),
  getKey = function(name)
    return luajava.bindClass("engine.input.Input"):getKeyByName(name);
  end,
  isLeftClick = function()
    return luajava.bindClass("engine.input.Input"):isLeftClickDown();
  end,
  getMouseWorldPos = function(camera)
    return luajava.bindClass("engine.input.Input"):getMouseWorldPosition(camera);
  end,
  getMouseScreenPos = function()
    return luajava.bindClass("engine.input.Input"):getMouseScreenPosition();
  end,
}
