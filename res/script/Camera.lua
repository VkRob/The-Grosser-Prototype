local lib = require("script.Engine");

function MoveCamera()

  local speed = 4;

  if(Input.getKey("SHIFT"):isDown()) then
    speed = 16;
  end

  if (Input.getKey("UP"):isDown()) then
    camera:getPosition().y = camera:getPosition().y + speed;
  end

  if (Input.getKey("DOWN"):isDown()) then
    camera:getPosition().y = camera:getPosition().y - speed;
  end

  if (Input.getKey("LEFT"):isDown()) then
    camera:getPosition().x = camera:getPosition().x - speed;
  end

  if (Input.getKey("RIGHT"):isDown()) then
    camera:getPosition().x = camera:getPosition().x + speed;
  end


end
