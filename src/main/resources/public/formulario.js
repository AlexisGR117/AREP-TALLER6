function loadGetMsg() {
    let msgVar = document.getElementById("msg").value;
    const xhttp = new XMLHttpRequest();
    xhttp.onload = function() {
        const table = document.getElementById("registros");
        const bodyTable = table.querySelector("tbody");
        bodyTable.innerHTML = "";
        const logs = JSON.parse(this.responseText).logs;
        logs.forEach(log => {
          const newRow = document.createElement("tr");
          const cellDate = document.createElement("td");
          const cellDescription = document.createElement("td");
          cellDate.textContent = log.date;
          cellDescription.textContent = log.description;
          newRow.appendChild(cellDate);
          newRow.appendChild(cellDescription);
          bodyTable.appendChild(newRow);
        });
    }
    xhttp.open("GET", "/logservicefacade?msg="+msgVar);
    xhttp.send();
}