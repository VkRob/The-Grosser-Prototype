-- This file functions similar to a header file in C.
-- It links methods and classes in the Java parts of the program to
-- Lua objects and methods for easy reference.

Engine = {

    Scene = {
      ManagerClass = luajava.bindClass("engine.logic.SceneManager");
      Class = luajava.bindClass("engine.logic.Scene");

      ManagerInstance = luajava.bindClass("engine.Engine").sceneManager;
      CurrentScene = luajava.bindClass("engine.Engine").sceneManager:getCurrentScene();
      Camera = luajava.bindClass("engine.Engine").sceneManager:getCurrentScene():getCamera();
    },

    --The Maths Sub-Struct, primarily for JOGL bindings
    Math = {

      --Returns a Vector2f of x and y
      Vector2f = function(x, y)
        return luajava.newInstance("org.joml.Vector2f", x, y);
      end,
    },

    --The Input Sub-Struct
    Input = {
      Class = luajava.bindClass("input.Input"),
      getKey = function(name)
        return luajava.bindClass("input.Input"):getKeyByName(name);
      end,
      isLeftClick = function()
        return luajava.bindClass("input.Input"):isLeftClickDown();
      end,
      getMouseWorldPos = function(camera)
        return luajava.bindClass("input.Input"):getMouseWorldPosition(camera);
      end,
    },

    --The Entity Sub-Struct
    Entity = {
      Class = luajava.bindClass("engine.entity.Entity");

      EntitySprite = function()
        return luajava.newInstance("engine.entity.EntitySprite");
      end,
      --Returns a TileEntity of specified id and position
      TileEntity = function(pos, id)
        return luajava.newInstance("engine.entity.TileEntity", pos, id);
      end,
      --Returns a TileEntity of specified id and position
      TileEntity = function(x, y, id)
        return luajava.newInstance("engine.entity.TileEntity", Engine.Math.Vector2f(x, y), id);
      end,
    },

    --The Tile Sub-Struct
    Tile = {

      --The Tile Class (used for referencing static methods)
      Class = luajava.bindClass("engine.tile.Tile"),

      --Returns a tile ID (can also be accessed with Tile.Class.getTileID(name), but this is a "shortcut")
      getTileID = function(name)
        return Engine.Tile.Class:getTileID(name);
      end,

    },
};
