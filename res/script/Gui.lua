local lib = require("script.Engine");

--Returns a button struct
function new_Button(x, y, width, height)
  local Button = {};
  Button.x = x*40;
  Button.y = y*40;
  Button.width = width;
  Button.height = height;
  return Button;
end

function new_ButtonEntity(x, y, width, height, listener)
  local ButtonEntity = {};
  ButtonEntity.button = new_Button(x,y,width,height);
  ButtonEntity.sprite = new_EntitySprite();
  ButtonEntity.listener = listener;
  setSpriteToButton(ButtonEntity.sprite, ButtonEntity.button);
  return ButtonEntity;
end

--Takes a button struct, returns a EntitySprite
function setSpriteToButton(sprite, button)
  sprite:setType(Entity.TYPE_GUI);
  sprite:setUsesTexture(false);
  sprite:setPosition(new_Vector2f(button.x,button.y));
  sprite:setDimensions(new_Vector2f(button.width,button.height));
end

function updateButton(button)
  if(isButtonPressed(button.button))then
    button.listener.onHover(button);
    if(Input.isLeftTap())then
      button.listener.onClick(button);
    end
  else
    button.listener.onNothing(button);
  end
end

--Returns if the button is pressed
function isButtonPressed(it)

  local mouseX = Input.getMouseScreenPos().x;
  local mouseY = Input.getMouseScreenPos().y;

  local myWidth = it.width*80;
  local myHeight = it.height*80;
  local myX = it.x-myWidth/2;
  local myY = it.y-myHeight/2;

  if(mouseX >= myX and mouseX <= myX+myWidth) then
    if(mouseY >= myY and mouseY <= myY+myHeight) then
      return true;
    end
  end
  return false;
end
