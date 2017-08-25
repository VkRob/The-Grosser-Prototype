local lib = require("script.Engine");
local cameraMover = require("script.Camera");

function round(num)
  return math.floor(num + 0.5);
end

local gui = {
  tileType;
};

local function getVar(hEditor, id)
  return hEditor:getScriptVars():get(id);
end

local function addVar(hEditor, obj)
  hEditor:getScriptVars():add(obj);
  return hEditor:getScriptVars():size()-1;
end

local function createVar(hEditor, obj)
  return getVar(hEditor, addVar(hEditor, obj));
end

function Init(hEditor)
  gui.tileType = createVar(hEditor, Engine.Entity.EntitySprite());
  hEditor:addEntity(gui.tileType);
end

function Run(hEditor)
  gui.tileType = getVar(hEditor, 0);

  gui.tileType:setPosition(Engine.Math.Vector2f(100, 40));
  gui.tileType:setType(Engine.Entity.Class.TYPE_GUI);

  if(Engine.Input.getKey("A"):isDown()) then
    gui.tileType:setTexCoords(Engine.Math.Vector2f(1,0));
    hEditor:setCursorTileID(1);
  end

  if(Engine.Input.getKey("D"):isDown()) then
    gui.tileType:setTexCoords(Engine.Math.Vector2f(0,0));
    hEditor:setCursorTileID(0);
  end


  -- Move the camera with the arrow keys
  MoveCamera();

  -- Set the cursor position to the tile nearest the mouse position
  local camera = Engine.Scene.Camera;
  local cursor = hEditor:getTileToPlace();
  local mouseWorldPos = Engine.Input.getMouseWorldPos(camera);
  mouseWorldPos.x = round(mouseWorldPos.x/40)*40;
  mouseWorldPos.y = round(mouseWorldPos.y/40)*40;
  cursor:setPosition(mouseWorldPos);

  -- If the mouse is clicked, set the selected tile
  if(Engine.Input.isLeftClick()) then
    local cursorTilePos = Engine.Math.Vector2f(mouseWorldPos.x / 40, mouseWorldPos.y / 40);
    local tileAtPos = hEditor:getWorld():getTileAtPos(cursorTilePos);
    if (not (tileAtPos == nil)) then
      tileAtPos:setTileType(hEditor:getCursorTileID());
    end
  end
end
