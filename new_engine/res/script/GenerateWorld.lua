local lib = require("script.Engine");

local world;

local polySize = 3;
local cellSize = 1;

function round(num)
  return math.floor(num + 0.5);
end

function createList(size)
  local grid = {};
  for i = 0, size-1 do
    grid[i] = {};
    for j = 0, size-1 do
      grid[i][j] = 0; -- Fill the values here
    end
  end
  return grid;
end

function createPoly()
  local grid = {};
  for i = 0, polySize-1 do
    grid[i] = {};
    for j = 0, polySize-1 do
      grid[i][j] = 0; -- Fill the values here
    end
  end
  return grid;
end

function rotatePoly(poly)
  local grid = createPoly();
  for i = 0, polySize-1 do
    for j = 0, polySize-1 do
      grid[i][polySize-1-j]=poly[j][i];
    end
  end
  return grid;
end


function addPolyToWorld(poly, posx, posy)
  for x = 0, polySize-1, 1 do
    for y = 0, polySize-1, 1 do
      if(poly[x][y] == 1) then
        local id = Engine.Tile.getTileID("stone");
        local xOffset = (cellSize-1)*x;
        local yOffset = (cellSize-1)*y;
        for cx = 0, cellSize-1, 1 do
          for cy = 0, cellSize-1, 1 do
            world:getTiles():add(Engine.Entity.TileEntity(x+xOffset+cx+posx, y+yOffset+cy+posy, id));
          end
        end
      end
    end
  end
end

function convertPoly(poly)
  if(poly[0][2] and poly[2][0] and poly[2][2] == 0) then
    r = {};
    r.x = 2;
    r.y = 2;
    return r;
  end
  r = {};
  r.x = 3;
  r.y = 3;
  return r;
end

-- Generate a 10x10 area of grass & stone blocks
function GenerateWorld(w)
  world = w;

  --Create all the needed polys

  --The Line Poly
  polyA = createPoly();
  polyA[0][0]=1;
  polyA[1][0]=1;
  polyA[2][0]=1;

  polyB = rotatePoly(polyA);
  polyC = rotatePoly(polyB);
  polyD = rotatePoly(polyC);

  --The L Poly
  polyE = createPoly();
  polyE[0][0]=1;
  polyE[1][0]=1;
  polyE[0][1]=1;

  polyF = rotatePoly(polyE);
  polyG = rotatePoly(polyF);
  polyH = rotatePoly(polyG);

  --Init the math random lib
  math.randomseed(os.time());
  --Call random a few times -workaround to some os bugs resulting in non-random values
  math.random();math.random();math.random();

  local tArea = 3;--in polys

  offsetX = 0;
  offsetY = 0;
  for i=0, tArea-1, 1 do
    dir = 0;--math.random(0,0);
    if(dir == 0) then --up
      if(math.random(0,1)==0)then
        print ("adding |");
        if(math.random(0,1)==0)then
          addPolyToWorld(polyB, offsetX, offsetY);
          offsetY = offsetY + 3*cellSize;
        else
          addPolyToWorld(polyC, offsetX, offsetY);
          offsetY = offsetY + 1*cellSize;
        end
    else
      print ("adding L");
      offsetY = offsetY - cellSize;
      if(offsetY < 0)then
        offsetY = 0;
      end
      addPolyToWorld(polyF, offsetX, offsetY);
      offsetY = offsetY + 3*cellSize;
    end
    end
  end
end
