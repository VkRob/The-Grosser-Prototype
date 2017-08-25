local lib = require("script.Engine");

-- Generate a 10x10 area of grass & stone blocks
function GenerateWorld(world)
  for x = 0, 10, 1 do
    for y = 0, 10, 1 do
      local id;
      if(x%2 == 0) then
        id = Engine.Tile.getTileID("grass");
      else
        id = Engine.Tile.getTileID("stone");
      end
      world:getTiles():add(Engine.Entity.TileEntity(x, y, id));
    end
  end
end
