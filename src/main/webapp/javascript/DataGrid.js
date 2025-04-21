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
    const deleteImg = document.createElement('img');
    deleteImg.src = '/images/delete.png';
    deleteImg.className = 'delete';
    deleteImg.style.width = '20px';
    deleteImg.style.cursor = 'pointer';
    deleteImg.title = "Delete User";

    deleteImg.addEventListener('click', () => {
        console.log("id name " + params.data.email);
        if (window.deleteRowFromGWT) {
            window.deleteRowFromGWT(params.data.email, function () {
                params.api.applyTransaction({ remove: [params.data] });
            });
        }
    });

    const span = document.createElement('span');
    span.className = 'imgSpanLogo';
    span.appendChild(deleteImg);

    this.eGui = span;
};

DeleteLogoRenderer.prototype.getGui = function () {
    return this.eGui;
};

DeleteLogoRenderer.prototype.refresh = function () {
    return false;
};
