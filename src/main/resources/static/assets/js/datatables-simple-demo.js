window.addEventListener('DOMContentLoaded', event => {

    const datatablesSimple = document.getElementById('datatablesSimple');
    if (datatablesSimple) {
        new simpleDatatables.DataTable(datatablesSimple);
    }

    const quizdatatablesSimple = document.getElementById('quizdatatablesSimple');
    if (quizdatatablesSimple) {
        new simpleDatatables.DataTable(quizdatatablesSimple);
    }
});
