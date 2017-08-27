local lib = require("script.Engine");
local cameraMover = require("script.Camera");

local gui = {
  tileType;
};

function Init(hEditor)
  gui.tileType = createVar(hEditor, new_EntitySprite());
  hEditor:addEntity(gui.tileType);
end

function Run(hEditor)
  gui.tileType = getVar(hEditor, 0);

  gui.tileType:setPosition(new_Vector2f(100, 40));
  gui.tileType:setType(Entity.TYPE_GUI);

  if(Input.getKey("A"):isDown()) then
    gui.tileType:setTexCoords(new_Vector2f(1,0));
    hEditor:setCursorTileID(1);
  end

  if(Input.getKey("D"):isDown()) then
    gui.tileType:setTexCoords(new_Vector2f(0,0));
    hEditor:setCursorTileID(0);
  end


  -- Move the camera with the arrow keys
  MoveCamera();

  -- Set the cursor position to the tile nearest the mouse position
  local cursor = hEditor:getTileToPlace();
  local mouseWorldPos = Input.getMouseWorldPos(camera);
  mouseWorldPos.x = round(mouseWorldPos.x/40)*40;
  mouseWorldPos.y = round(mouseWorldPos.y/40)*40;
  cursor:setPosition(mouseWorldPos);

  -- If the mouse is clicked, set the selected tile
  if(Input.isLeftClick()) then
    local cursorTilePos = new_Vector2f(mouseWorldPos.x / 40, mouseWorldPos.y / 40);
    local tileAtPos = hEditor:getWorld():getTileAtPos(cursorTilePos);
    if (not (tileAtPos == nil)) then
      tileAtPos:setTileType(hEditor:getCursorTileID());
    end
  end
end
