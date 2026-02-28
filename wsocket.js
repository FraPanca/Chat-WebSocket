const socket = new WebSocket("ws://localhost:8080/Esame_20_10_2022_es3/actions");

var nome;
var group;
const colors = {};

// ricezione messaggio dal server
socket.onmessage = function(event) {
	
	var message = JSON.parse(event.data);
	if(message.group !== group) return;
	
	console.log(event.data);
	
	const result = document.getElementById("messaggi");
	
	if(message.previousMsg && message.previousMsg.length > 0) {
		for(let prevMsg of message.previousMsg) {
			var par = document.createElement("p");
			var span1 = document.createElement("span");
			var span2 = document.createElement("span");
			
			span1.innerText = prevMsg.nome;
			span2.innerText = ((prevMsg.nome === "") ? "" : ": ") + prevMsg.msg;
			
			span1.style.color = getColor(prevMsg.nome);
			
			par.appendChild(span1);
			par.appendChild(span2);
			result.appendChild(par);
		}
		
	} else {
		const par = document.createElement("p");
		const span1 = document.createElement("span");
		const span2 = document.createElement("span");
		
		span1.innerText = message.nome;
		span2.innerText = ": " + message.msg;
		
		span1.style.color = getColor(message.nome);
		
		par.appendChild(span1);
		par.appendChild(span2);
		result.appendChild(par);
	}
}

// invio della stringa
function accedi() {
	const action = "accedi";
	const msg = "";

	nome = document.getElementById("nome").value;
	group = document.getElementById("group").value;
	
	const data = {nome, group, msg, action};
	send(data);
	
	document.getElementById("accesso").style.display = "none";
	document.getElementById("chat").style.display = "flex";
}

function inviaMsg() {
	const action = "invio";

	const msg = document.getElementById("msg").value;
	if(!msg) return;
	
	const data = {nome, group, msg, action};
	send(data);
	
	document.getElementById("msg").value = "";
}


function send(msg) {
	var json = JSON.stringify(msg);
	
	console.log(json);
	socket.send(json);
}


function getColor(name) {
  if(!colors[name]) colors[name] = randomColor();
  return colors[name];
}

function randomColor() {
  const hue = Math.floor(Math.random() * 360);
  const saturation = 70;
  const lightness = 50;

  return `hsl(${hue}, ${saturation}%, ${lightness}%)`;
}

