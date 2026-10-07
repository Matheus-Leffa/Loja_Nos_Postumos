"use strict";

// This collection can be replaced by the API response without changing the card renderer.
const products = [
    {
        id: "saia-folha",
        name: "Saia Folha",
        description: "Saia leve com textura delicada e movimento natural.",
        price: 189.90,
        image: "../assets/products/saia-folha.svg",
        imageAlt: "Saia de crochê em tom verde sálvia"
    },
    {
        id: "casaco-brisa",
        name: "Casaco Brisa",
        description: "Casaco aberto, macio e versátil para dias amenos.",
        price: 249.90,
        image: "../assets/products/casaco-brisa.svg",
        imageAlt: "Casaco artesanal de crochê em tom creme"
    },
    {
        id: "blusa-trama",
        name: "Blusa Trama",
        description: "Blusa de trama arejada para compor looks atemporais.",
        price: 159.90,
        image: "../assets/products/blusa-trama.svg",
        imageAlt: "Blusa de crochê em verde escuro"
    },
    {
        id: "top-aurora",
        name: "Top Aurora",
        description: "Peça delicada com acabamento manual e caimento suave.",
        price: 119.90,
        image: "../assets/products/top-aurora.svg",
        imageAlt: "Top artesanal de crochê em verde sálvia"
    }
];

const productGrid = document.querySelector(".product-grid");

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

function renderProducts(items) {
    const fragment = document.createDocumentFragment();

    items.forEach((product) => {
        fragment.appendChild(createProductCard(product));
    });

    productGrid.replaceChildren(fragment);
}

renderProducts(products);
