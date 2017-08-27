local lib = require("script.Engine");

-- Generate a 10x10 area of grass & stone blocks
function GenerateWorld(world)
  for x = 0, 10, 1 do
    for y = 0, 10, 1 do
      local id;
      if(x%2 == 0) then
        id = 0;
      else
        id = 1;
      end
      world:getTiles():add(new_TileEntityXY(x, y, id));
    end
  end
end
