local lib_engine = require("script.Engine");
local lib_gui = require("script.Gui");

local gui = {
  btn_editor;
};

local button_listener = {
  onHover = function(buttonEntity)
    buttonEntity.sprite:setTint(new_Vector3f(0.5,0.5,0.5));
  end,
  onClick = function(buttonEntity)
    Input.Class:disableHoldUntilRelease();
    current_scene:getEditor():setCamera(current_scene:getCamera());
    scene_manager:setCurrentScene(current_scene:getEditor());
  end,
  onNothing = function(buttonEntity)
    buttonEntity.sprite:setTint(new_Vector3f(1, 1, 1));
  end,
};

function Init(scene)
  gui.btn_editor = new_ButtonEntity(2.1, 14.4, 2, 0.5, button_listener);
  scene:addEntity(gui.btn_editor.sprite);
end

function Loop(scene)
  updateButton(gui.btn_editor);
end
