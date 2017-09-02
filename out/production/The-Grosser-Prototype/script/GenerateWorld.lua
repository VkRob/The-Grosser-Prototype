local lib = require("script.Engine");

local chunkSize = 10;

-- Generate a 10x10 area of grass & stone blocks
function GenerateChunk(world, chunk_pos)
  world:setChunkSize(chunkSize);
  for x = 0, chunkSize-1, 1 do
    for y = 0, chunkSize-1, 1 do
      local id;
      if(x%10 == 0 or y%10 == 0) then
        id = 2;
      else
        id = 1;
      end
      world:getTiles():add(new_TileEntityXY(chunk_pos.x + x, chunk_pos.y + y, id));
    end
  end
end
