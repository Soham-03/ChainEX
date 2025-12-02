# zapUPI – Pay the Indian Way, Right Away 🇮🇳

Empowering foreign tourists to use UPI anywhere in India without needing an Indian bank account – fully digital, fully compliant, and live in under 5 minutes.

---

## ✨ Overview

zapUPI is a cross-border fintech platform that lets foreign tourists onboard digitally with passport and visa, convert USD/EUR to INR at transparent rates, and spend locally using UPI. The goal is to make “paying like a local” in India as simple as installing an app and completing a short video-KYC journey.

---

## 🚨 Problem

Foreign tourists in India are effectively locked out of the country’s everyday payment rails:

- Cash is risky, easy to lose, and small vendors often lack change.
- International debit/credit/forex cards are accepted only in limited, mostly urban locations.
- Opening an Indian bank account for UPI is slow, paperwork-heavy, and impractical for short stays.
- Forex conversion is often opaque, with hidden markups and confusing taxes.
- Tourists cannot easily tap into India’s UPI-centric digital payments ecosystem.

---

## ✅ Solution (MVP)

zapUPI provides a fully digital, compliant on-ramp into UPI for foreign tourists:

1. Tourist downloads the zapUPI app and creates an account.
2. Completes Video-KYC (V-CIP) using passport and visa.
3. Loads funds in USD/EUR via card or bank transfer into a FEMA-compliant escrow account.
4. System fetches live FX rates (with fallback providers) and shows markup, fees, and taxes clearly.
5. GST/TCS is auto-computed and remitted to the government.
6. Net INR is credited into a full-KYC PPI wallet issued by regulated partners.
7. Tourist receives an interoperable UPI handle and can pay any UPI ID/QR in India.

All major lifecycle events are recorded on a permissioned DLT (Hyperledger Fabric), ensuring immutability and auditability without exposing user PII.

---

## 🧱 Architecture Overview

**Core pillars:**

- 🔐 Compliance-first  
  Designed around RBI KYC Master Directions, RBI PPI Master Directions, FEMA rules, NPCI UPI mandates, and applicable Income Tax and GST/TCS norms.

- ⚙️ Real-time rails  
  Live FX rate fetching (primary source plus fallback providers), on-the-fly fee and tax computation, and just-in-time e-tax remittance.

- 🪪 Identity  
  RBI-compliant Video-KYC (V-CIP) for remote onboarding of foreign nationals using passport and visa details.

- 💳 Money movement  
  Funds flow via escrow and licensed PPI partners into INR wallets that can be used nationally.

- 🔗 UPI interoperability  
  Tourists receive UPI handles that work across the existing Indian merchant ecosystem, from street vendors to online platforms.

- ⛓️ Auditability  
  Hyperledger Fabric-based permissioned DLT logs critical events immutably for regulators and partners, without storing raw PII on-chain.

---

## 🛠 Tech Stack

- **Mobile App**
  - Kotlin / Jetpack Compose
  - Kotlin Multiplatform-ready for future iOS support
  - Encrypted local storage (e.g., Room with SQLCipher or equivalent)

- **Backend**
  - Node.js for REST APIs and business logic
  - PostgreSQL as the primary relational database
  - Optional caching layer (e.g., Redis) for performance and FX data

- **Integrations**
  - Video-KYC provider (V-CIP)
  - Stripe / Adyen or equivalent for card payments
  - Primary FX provider (e.g., RBI/FBIL rates) with fallback APIs
  - PPI issuers (e.g., Yes Bank / ICICI / M2P)
  - NPCI UPI rails for payment interoperability
  - Hyperledger Fabric network for DLT-based audit logging

---

## 🔄 User Journey

- Download zapUPI and sign up.
- Complete video-KYC (passport + visa).
- Load $500 (or other amount) via international card into FEMA-compliant escrow.
- App shows real-time FX rate, markup, and all fees/taxes.
- System auto-computes GST/TCS and pays it via e-tax portal.
- Net INR is credited into a full-KYC PPI wallet.
- Tourist gets a UPI handle and pays any UPI merchant/QR in India.
---


---

## 🏆 Achievements & Recognition

This project has moved beyond a typical academic assignment and has been validated in real innovation environments:

- 💼 **VC-backed MVP**  
  zapUPI is in MVP phase with backing and support from the VC firm **Vsure**.

- 🧠 **Institution’s Innovation Council (IIC) Summit**  
  Selected by the college’s IIC summit to be presented in front of visiting guests, industry professionals, and innovators from across Maharashtra.

- 🦈 **Shark Tank Winner**  
  Won the college-level Shark Tank event for its combination of technical depth, regulatory understanding, and business viability.

- 🏙 **JP Morgan Innovation Week – Mumbai**  
  Selected for **Innovation Week at JP Morgan, Mumbai**, and presented within JP Morgan’s premises to senior professionals and experts.

---

## 👨‍💻 Authors
**Soham Parab**
Tech Lead / Full-Stack Developer
Email: sohamparab1003@gmail.com





