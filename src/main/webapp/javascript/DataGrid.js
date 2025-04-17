if (!window.ds) window.ds = {};

window.ds.DataAgGridPanel = {
    createDataGrid: function (id, rowData) {
        console.log("Calling from JSInterop");

        const gridOptions = {
            rowData: rowData,
            defaultColDef: {
                editable: true,
                flex: 1,
                filter: true,
                floatingFilter: true
            },
            columnDefs: [
                { field: "name" },
                { field: "email" },
                { field: "password" },
                { field: "contact" },
                {
                    field: "delete",
                    cellRenderer: DeleteLogoRenderer,
                    minWidth: 100
                }
            ],
            components: {
                DeleteLogoRenderer: DeleteLogoRenderer
            }
        };

        const element = document.getElementById(id);
        if (element) {
            window.ds.gridApi = agGrid.createGrid(element, gridOptions);
        } else {
            console.error("Element not found for ID:", id);
        }
    }
};

function DeleteLogoRenderer() {}

DeleteLogoRenderer.prototype.init = function (params) {
    const img = document.createElement('img');
    img.src = '/images/delete.png';
    img.className = 'delete';
    img.style.width = '20px';
    img.style.cursor = 'pointer';
    img.title = "Delete User";

    img.addEventListener('click', () => {
        console.log("id name " + params.data.email);
        if (window.deleteRowFromGWT) {
            window.deleteRowFromGWT(params.data.email, function () {
                params.api.applyTransaction({ remove: [params.data] });
            });
        }
    });

    const span = document.createElement('span');
    span.className = 'imgSpanLogo';
    span.appendChild(img);

    this.eGui = span;
};

DeleteLogoRenderer.prototype.getGui = function () {
    return this.eGui;
};

DeleteLogoRenderer.prototype.refresh = function () {
    return false;
};
