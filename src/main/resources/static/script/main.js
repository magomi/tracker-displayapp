// ------------------------------------
// handle websocket messages
// ------------------------------------
var url = "ws://localhost:8080/websock";
var socket = new SockJS('http://localhost:8080/websock');
var client = Stomp.over(socket);
var position = {x: 0, y: 0, z: 0};

var scene;
var block;

// ------------------------------------
// show the 3d model
// ------------------------------------
var canvas = document.getElementById("renderCanvas"); // Get the canvas element
var engine = new BABYLON.Engine(canvas, true); // Generate the BABYLON 3D engine

/******* Add the create scene function ******/
var createScene = function () {

  // Create the scene space
  var scene = new BABYLON.Scene(engine);

  // Add a camera to the scene and attach it to the canvas
  var camera = new BABYLON.ArcRotateCamera("Camera", Math.PI / 2, Math.PI / 2, 2, new BABYLON.Vector3(0, 0, 0), scene);
  // var camera = new BABYLON.AnaglyphUniversalCamera("Camera", new BABYLON.Vector3(5, 5, -15), 0.03, scene);
  camera.attachControl(canvas, true);

  // Add lights to the scene
  var light1 = new BABYLON.HemisphericLight("light1", new BABYLON.Vector3(1, 1, 0), scene);
  var light2 = new BABYLON.PointLight("light2", new BABYLON.Vector3(0, 1, -1), scene);

  block = BABYLON.MeshBuilder.CreateBox("box", { size: 1 }, scene);
  // Add and manipulate meshes in the scene
  // var sphereA = BABYLON.MeshBuilder.CreateBox("box", { size: 1 }, scene);
  // var material = new BABYLON.StandardMaterial("sphereAMaterial", scene);
  // material.wireframe = true;
  // sphereA.material = material;

  // var sphereB = BABYLON.MeshBuilder.CreateSphere("sphereB", { diameter: 2 }, scene);

  // var animationA = new BABYLON.Animation("moveSphereA", "position", 30, BABYLON.Animation.ANIMATIONTYPE_VECTOR3, BABYLON.Animation.ANIMATIONLOOPMODE_CYCLE);
  // var keysA = [];
  // keysA.push({
  //   frame: 0,
  //   value: BABYLON.Vector3.Zero
  // });
  // keysA.push({
  //   frame: 500,
  //   value: new BABYLON.Vector3(10, 10, 10)
  // });
  // animationA.setKeys(keysA);
  // sphereA.animations = [];
  // sphereA.animations.push(animationA);


  // var animationBoxB = new BABYLON.Animation("moveSphereB", "position", 30, BABYLON.Animation.ANIMATIONTYPE_VECTOR3, BABYLON.Animation.ANIMATIONLOOPMODE_CYCLE);
  // var keysB = [];
  // keysB.push({
  //   frame: 0,
  //   value: new BABYLON.Vector3(0, 10, 2)
  // });
  // keysB.push({
  //   frame: 500,
  //   value: new BABYLON.Vector3(10, 2, 8)
  // });
  // animationBoxB.setKeys(keysB);
  // sphereB.animations = [];
  // sphereB.animations.push(animationBoxB);

  // scene.beginAnimation(sphereA, 0, 500, true);
  // scene.beginAnimation(sphereB, 0, 500, true);


  return scene;
};



/******* End of the create scene function ******/

var scene = createScene(); //Call the createScene function

// Register a render loop to repeatedly render the scene
engine.runRenderLoop(function () {
  scene.render();
});

// Watch for browser/canvas resize events
window.addEventListener("resize", function () {
  engine.resize();
});


subscription_callback = function (message) {
  // called when the client receives a STOMP message from the server
  if (message.body) {
    console.log(message.body);
    var rawPosition = JSON.parse(message.body);
    console.log(position);
    block.rotation.x = position.x - rawPosition.x/256;
    block.rotation.y = position.y - rawPosition.y/256;
    block.rotation.z = position.z - rawPosition.z/256;
    position.x = rawPosition.x/256;
    position.y = rawPosition.y/256;
    position.z = rawPosition.z/256;
  } else {
    alert("got empty message");
  }
};

var connect_callback = function () {
  alert("connected");
  client.subscribe("/position/acceleration", subscription_callback);
};

var error_callback = function (error) {
  alert(error);
};

client.reconnect_delay = 5000;

client.connect("", "", connect_callback, error_callback);
