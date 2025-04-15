if (!window.js) window.js = {}
window.js.AgGridPanel = {

    createAgGrid: function (id, rowData, columnDefs) {
        console.log("calling from JSInterop")
        let gridOptions = {
            rowData: rowData,
            defaultColDef: {
                editable: true,
                flex: 1,
                filter: true,
                floatingFilter: true
            },
            columnDefs: columnDefs,
        };
        console.log("received id " + id);
        let element = document.getElementById(id);
        console.log("element is " + element);
        gridApi = window.agGrid.createGrid(element, gridOptions);
    }
}