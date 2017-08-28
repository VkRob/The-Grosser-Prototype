local lib = require("script.Engine");
local cameraMover = require("script.Camera");

local numOftiles = 3;

--local selected_tile_var_id = 0;
--local tile_list_var_id = 1;

local gui = {
  selected_tile,
  tile_list,
  cursor,
};

function Init(hEditor)
  gui.selected_tile = createVar(hEditor, new_EntitySprite());
  gui.tile_list = createVar(hEditor, new_ArrayList());
  gui.cursor = createVar(hEditor, new_EntitySprite());

  hEditor:addEntity(gui.cursor);

  local background = new_EntitySprite();
  background:setType(Entity.TYPE_GUI);
  background:setDimensions(new_Vector2f(1, 8));
  background:setPosition(new_Vector2f((1*40),(8*40)-20));
  background:setTint(new_Vector3f(0.7,0.5,0.3));
  background:setUsesTexture(false);
  hEditor:addEntity(background);

  for i=1, numOftiles, 1 do
    local e = new_EntitySprite();
    e:setType(Entity.TYPE_GUI);
    gui.tile_list:add(e);
    hEditor:addEntity(e)
  end

  gui.selected_tile:setType(Entity.TYPE_GUI);
  hEditor:addEntity(gui.selected_tile);



end

function Run(hEditor)

  gui.selected_tile = getVar(hEditor, 0);
  gui.tile_list = getVar(hEditor, 1);
  gui.cursor = getVar(hEditor, 2);

  local mouseX = Input.getMouseScreenPos().x;
  local mouseY = Input.getMouseScreenPos().y;

  --[FOR every gui tile, check if it is being clicked on by the mouse]

  --TODO: Handle Aspect Ratios
  for i=0, numOftiles-1, 1 do
    local it = gui.tile_list:get(i);
    it:setPosition(new_Vector2f(40,500-i*40));
    it:setTexCoords(new_Vector2f(i-1,0));

    local myWidth = 40;
    local myHeight = 40;
    local myX = it:getPosition().x-myWidth/2;
    local myY = it:getPosition().y-myHeight/2;
    if(mouseX >= myX and mouseX <= myX+myWidth) then
      if(mouseY >= myY and mouseY <= myY+myHeight) then
      --Change visual effect
      end
    end
    local isMouseOver = false;
    if(mouseX >= myX and mouseX <= myX+myWidth) then
      if(mouseY >= myY and mouseY <= myY+myHeight) then
        isMouseOver = true;
      end
    end
    if(isMouseOver)then
      it:setTint(new_Vector3f(0.5,0.5,0.5));
      it:setDimensions(new_Vector2f(0.75*0.5, 0.75*0.5));
      if(Input.isLeftClick())then
        hEditor:setCursorTileID(i);
      end
    else
      it:setTint(new_Vector3f(1,1,1));
      it:setDimensions(new_Vector2f(0.5,0.5));
    end
  end

  gui.selected_tile:setPosition(new_Vector2f(40, 40));

  gui.selected_tile:setTexCoords(new_Vector2f(hEditor:getCursorTileID()-1,0));
  -- Move the camera with the arrow keys
  MoveCamera();


  if mouseX > 80 then
    -- Set the cursor position to the tile nearest the mouse position
    local mouseWorldPos = Input.getMouseWorldPos(camera);
    mouseWorldPos.x = round(mouseWorldPos.x/40)*40;
    mouseWorldPos.y = round(mouseWorldPos.y/40)*40;

    local cursorTilePos = new_Vector2f(mouseWorldPos.x / 40, mouseWorldPos.y / 40);
    local tileAtPos = hEditor:getWorld():getTileAtPos(cursorTilePos);

    gui.cursor:setType(Entity.TYPE_GUI);
    gui.cursor:setPosition(new_Vector2f(-100,-100));
    -- If the mouse is clicked, set the selected tile
    if (not(tileAtPos == nil)) then
      gui.cursor:setType(Entity.TYPE_SPRITE);
      gui.cursor:setPosition(mouseWorldPos);
      gui.cursor:setTexCoords(tileAtPos:getTileType():getTextureCoords());
      gui.cursor:setTint(new_Vector3f(0.5,0.5,0.5));
      if(Input.isLeftClick()) then
        tileAtPos:setTileType(hEditor:getCursorTileID());
      end
    end
  else
    gui.cursor:setPosition(new_Vector2f(-100,0));
  end
end 