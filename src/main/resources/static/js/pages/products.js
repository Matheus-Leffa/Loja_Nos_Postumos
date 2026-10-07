"use strict";

// This collection can be replaced by the API response without changing the card renderer.
const products = [
    {
        id: "saia-folha",
        name: "Saia Folha",
        description: "Saia leve com textura delicada e movimento natural.",
        price: 189.90,
        image: "../assets/products/saia-folha.svg",
        imageAlt: "Saia de crochê em tom verde sálvia",
        measurements: {
            bust: { P: "A informar", M: "A informar", G: "A informar" },
            waist: { P: "A informar", M: "A informar", G: "A informar" },
            length: { P: "A informar", M: "A informar", G: "A informar" }
        }
    },
    {
        id: "casaco-brisa",
        name: "Casaco Brisa",
        description: "Casaco aberto, macio e versátil para dias amenos.",
        price: 249.90,
        image: "../assets/products/casaco-brisa.svg",
        imageAlt: "Casaco artesanal de crochê em tom creme",
        measurements: {
            bust: { P: "A informar", M: "A informar", G: "A informar" },
            waist: { P: "A informar", M: "A informar", G: "A informar" },
            length: { P: "A informar", M: "A informar", G: "A informar" }
        }
    },
    {
        id: "blusa-trama",
        name: "Blusa Trama",
        description: "Blusa de trama arejada para compor looks atemporais.",
        price: 159.90,
        image: "../assets/products/blusa-trama.svg",
        imageAlt: "Blusa de crochê em verde escuro",
        measurements: {
            bust: { P: "A informar", M: "A informar", G: "A informar" },
            waist: { P: "A informar", M: "A informar", G: "A informar" },
            length: { P: "A informar", M: "A informar", G: "A informar" }
        }
    },
    {
        id: "top-aurora",
        name: "Top Aurora",
        description: "Peça delicada com acabamento manual e caimento suave.",
        price: 119.90,
        image: "../assets/products/top-aurora.svg",
        imageAlt: "Top artesanal de crochê em verde sálvia",
        measurements: {
            bust: { P: "A informar", M: "A informar", G: "A informar" },
            waist: { P: "A informar", M: "A informar", G: "A informar" },
            length: { P: "A informar", M: "A informar", G: "A informar" }
        }
    }
];

const productGrid = document.querySelector(".product-grid");
const measurementsModal = document.querySelector("[data-measurements-modal]");
const measurementsDialog = measurementsModal.querySelector(".measurements-modal");
const measurementsTitle = document.querySelector("#measurements-modal-title");
const measurementsTableBody = document.querySelector(".measurements-table tbody");
const closeModalButton = document.querySelector(".modal-close-button");
let lastFocusedElement;

function formatPrice(price) {
    return price.toLocaleString("pt-BR", {
        style: "currency",
        currency: "BRL"
    });
}

function createProductCard(product) {
    const card = document.createElement("article");
    card.className = "product-card";

    const image = document.createElement("img");
    image.className = "product-card-image";
    image.src = product.image;
    image.alt = product.imageAlt;
    image.loading = "lazy";

    const content = document.createElement("div");
    content.className = "product-card-content";

    const name = document.createElement("h2");
    name.className = "product-card-name";
    name.textContent = product.name;

    const description = document.createElement("p");
    description.className = "product-card-description";
    description.textContent = product.description;

    const price = document.createElement("p");
    price.className = "product-card-price";
    price.textContent = formatPrice(product.price);

    const actions = document.createElement("div");
    actions.className = "product-card-actions";

    const measuresButton = document.createElement("button");
    measuresButton.className = "button button-secondary";
    measuresButton.type = "button";
    measuresButton.dataset.productId = product.id;
    measuresButton.addEventListener("click", () => openMeasurementsModal(product, measuresButton));
    measuresButton.textContent = "Ver medidas";

    const cartButton = document.createElement("button");
    cartButton.className = "button";
    cartButton.type = "button";
    cartButton.dataset.productId = product.id;
    cartButton.textContent = "Adicionar ao carrinho";

    actions.append(measuresButton, cartButton);
    content.append(name, description, price, actions);
    card.append(image, content);

    return card;
}

function createMeasurementRow(label, values) {
    const row = document.createElement("tr");
    const labelCell = document.createElement("th");
    labelCell.scope = "row";
    labelCell.textContent = label;
    row.appendChild(labelCell);

    ["P", "M", "G"].forEach((size) => {
        const valueCell = document.createElement("td");
        valueCell.textContent = values[size];
        row.appendChild(valueCell);
    });

    return row;
}

function openMeasurementsModal(product, trigger) {
    lastFocusedElement = trigger;
    measurementsTitle.textContent = `Medidas — ${product.name}`;
    measurementsTableBody.replaceChildren(
        createMeasurementRow("Busto", product.measurements.bust),
        createMeasurementRow("Cintura", product.measurements.waist),
        createMeasurementRow("Comprimento", product.measurements.length)
    );
    measurementsModal.hidden = false;
    document.body.classList.add("modal-is-open");
    closeModalButton.focus();
}

function closeMeasurementsModal() {
    measurementsModal.hidden = true;
    document.body.classList.remove("modal-is-open");
    lastFocusedElement?.focus();
}

function trapModalFocus(event) {
    if (event.key !== "Tab") {
        return;
    }

    const focusableElements = measurementsDialog.querySelectorAll(
        "button, [href], input, select, textarea, [tabindex]:not([tabindex='-1'])"
    );
    const firstElement = focusableElements[0];
    const lastElement = focusableElements[focusableElements.length - 1];

    if (event.shiftKey && document.activeElement === firstElement) {
        event.preventDefault();
        lastElement.focus();
    } else if (!event.shiftKey && document.activeElement === lastElement) {
        event.preventDefault();
        firstElement.focus();
    }
}

closeModalButton.addEventListener("click", closeMeasurementsModal);
measurementsModal.addEventListener("click", (event) => {
    if (event.target === measurementsModal) {
        closeMeasurementsModal();
    }
});
document.addEventListener("keydown", (event) => {
    if (measurementsModal.hidden) {
        return;
    }

    if (event.key === "Escape") {
        closeMeasurementsModal();
    } else {
        trapModalFocus(event);
    }
});

function renderProducts(items) {
    const fragment = document.createDocumentFragment();

    items.forEach((product) => {
        fragment.appendChild(createProductCard(product));
    });

    productGrid.replaceChildren(fragment);
}

renderProducts(products);
