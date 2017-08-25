local lib = require("script.Engine");

function MoveCamera()

  local speed = 4;

  local camera = Engine.Scene.Camera;
  local input = Engine.Input;

  if (input.getKey("UP"):isDown()) then
    camera:getPosition().y = camera:getPosition().y + speed;
  end

  if (input.getKey("DOWN"):isDown()) then
    camera:getPosition().y = camera:getPosition().y - speed;
  end

  if (input.getKey("LEFT"):isDown()) then
    camera:getPosition().x = camera:getPosition().x - speed;
  end

  if (input.getKey("RIGHT"):isDown()) then
    camera:getPosition().x = camera:getPosition().x + speed;
  end

end
