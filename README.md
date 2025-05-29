# 📱 E-Commerce App – Guide Utilisateur

Bienvenue dans cette application mobile E-Commerce développée sous Android avec Kotlin.  
Cette application vous permet de parcourir une boutique en ligne, d’ajouter des articles à votre panier, de scanner QR code contenant un id du produit ou encore de simuler un paiement.

---

## 🛍️ Fonctionnalités Principales

### 🔍 Recherche d’articles
- Un champ de recherche permet de filtrer les produits par mot-clé (titre et description)
- Les résultats s’affichent dans une liste dynamique avec image, nom et prix

### 📄 Fiche produit
- En cliquant sur un produit, vous accédez à sa fiche détaillée contenant :
  - L’image en grand format
  - Le nom et la description du produit
  - Le prix
  - Un bouton **"Ajouter au panier"**

### 📦 Panier intelligent
- Le panier regroupe **un seul exemplaire de chaque produit**
- Pour chaque article, vous pouvez :
  - **Augmenter** ou **diminuer** la quantité avec des boutons + / -
  - **Supprimer** le produit du panier
  - Visualiser le **prix unitaire** et la **quantité**
- Le **total général** est affiché en bas de l’écran
- Accessible à tout moment via l’icône panier dans le header

### 📷 Scan QR Code
- En scannant un QR Code (avec l’ID d’un produit : nombre entre 1 et 20), l’application ouvre directement la fiche correspondante
- Technologie utilisée : **ZXing Android Embedded**

### 💳 Page de paiement (interface fictive)
- Avant de finaliser l’achat, une **page de paiement réaliste** s’affiche :
  - Récapitulatif complet du panier
  - Image, nom, quantité, prix et sous-total pour chaque produit
  - Total global
- Aucun paiement réel

---

## Expérience Utilisateur

- Navigation fluide entre les écrans
- Interface moderne avec header dynamique (titre + actions contextuelles)
- Adaptée aux smartphones Android
- De nombreux messages Toast ou snackabar s'affichent en fonction des décisions de l'utilisateur

---

##  API utilisée
> [FakeStore API](https://fakestoreapi.com/)  
Toutes les données (produits, images, prix, descriptions) sont récupérées en temps réel via cette API REST gratuite.

---

## Auteurs

Développé par **Ivan Cocusse et Lucas Bosq**,

Projet réalisé dans le cadre du cours de Matériels Mobiles enseigné par Cyril Dumont.
