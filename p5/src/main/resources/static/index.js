contadores = fetch("http://localhost:8080/contadores").then((response)=>{
    console.log(contadores);
    response.forEach(element => {
        const elementContador = 
        `<div>
            <label for="">Contador: `+element.nombre+` </label>
            <label for="" >`+element.valor+`</label>
        </div>`;
        const lista = document.getElementById("div-contadores");
        lista.innerHTML = lista.innerHTML + elementContador;
    })
})

