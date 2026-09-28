# Shattered Pixel Dungeon - النسخة العربية 🕌

[Shattered Pixel Dungeon](https://shatteredpixel.com/shatteredpd/) هي لعبة استكشاف زنزانات (Dungeon Crawler) بأسلوب Roguelike تقليدي مفتوحة المصدر، تتميز بمستويات وأعداء عشوائيين، ومئات الأغراض لجمعها واستخدامها. مبنية على [الكود المصدري للعبة Pixel Dungeon](https://github.com/00-Evan/pixel-dungeon-gradle) بواسطة [Watabou](https://watabou.itch.io/).

## 🌟 مميزات النسخة العربية (بواسطة عبدالله محمد بامطرف)

هذا المستودع يحتوي على تعريب كامل وشامل للعبة مع تحسينات مخصصة للغة العربية:

- **ترجمة كاملة 100%**: ترجمة جميع نصوص اللعبة بما في ذلك القوائم، الحوارات، وصف الأغراض، الوحوش، المواهب، والتحديات.
- **دعم اتجاه النص (RTL)**: تعديل محرك اللعبة لعرض النصوص من اليمين إلى اليسار بشكل صحيح.
- **خط عربي بكسلي مخصص**: إنشاء خط `pixel_font_ar.ttf` يتناسب مع الطابع البكسلي للعبة مع الحفاظ على وضوح الحروف العربية.
- **مُشكّل الحروف العربية (Arabic Reshaper)**: دمج مكتبات متخصصة لربط الحروف العربية وتشكيلها تلقائياً داخل محرك LibGDX.
- **قلب الأقواس والترقيم**: معالجة ذكية للأقواس وعلامات الترقيم لتظهر بشكل صحيح في البيئة المعكوسة.
- **بناء تلقائي متعدد المنصات**: إعداد GitHub Actions لبناء اللعبة تلقائياً لأندرويد، ويندوز، ولينكس.

## 👨‍💻 المطوّر والمعَرِّب

**عبدالله محمد بامطرف**  
تم تطوير هذا التعريب بحب لمجتمع اللاعبين العرب، بهدف تقديم تجربة لعب غامرة وكاملة باللغة العربية.

## 📦 التحميل والتثبيت

يمكنك تحميل أحدث نسخة من اللعبة من قسم [Releases](../../releases) في هذا المستودع:

- **🤖 أندرويد**: ملف `APK` جاهز للتثبيت المباشر.
- **🪟 ويندوز**: ملف مضغوط يحتوي على اللعبة مع بيئة جافا مدمجة (لا يتطلب تثبيت جافا).
- **🐧 لينكس**: ملف مضغوط مشابه لنسخة ويندوز.

> ⚠️ **ملاحظة لمستخدمي أندرويد**: هناك مشكلة بسيطة في عرض الأرقام داخل بعض قوائم اللعبة (تظهر كعلامات استفهام)، سيتم حلها في التحديث القادم بإذن الله. نسخة سطح المكتب تعمل بشكل مثالي حالياً.

## 🎮 عن اللعبة الأصلية

تعمل Shattered Pixel Dungeon حالياً على منصات Android و iOS و Desktop. يمكنك العثور على الإصدارات الرسمية من اللعبة على:

[![Get it on Google Play](https://shatteredpixel.com/assets/images/badges/gplay.png)](https://play.google.com/store/apps/details?id=com.shatteredpixel.shatteredpixeldungeon)
[![Download on the App Store](https://shatteredpixel.com/assets/images/badges/appstore.png)](https://apps.apple.com/app/shattered-pixel-dungeon/id1563121109)
[![Steam](https://shatteredpixel.com/assets/images/badges/steam.png)](https://store.steampowered.com/app/1769170/Shattered_Pixel_Dungeon/)<br>
[![GOG.com](https://shatteredpixel.com/assets/images/badges/gog.png)](https://www.gog.com/game/shattered_pixel_dungeon)
[![Itch.io](https://shatteredpixel.com/assets/images/badges/itch.png)](https://shattered-pixel.itch.io/shattered-pixel-dungeon)
[![Github Releases](https://shatteredpixel.com/assets/images/badges/github.png)](https://github.com/00-Evan/shattered-pixel-dungeon/releases)

إذا أعجبتك اللعبة، يرجى النظر في [دعم المطور الأصلي على Patreon](https://www.patreon.com/ShatteredPixel)!

يوجد مدونة رسمية لهذا المشروع على [ShatteredPixel.com](https://www.shatteredpixel.com/blog/).

اللعبة لديها أيضاً مشروع ترجمة مستضاف على [Transifex](https://explore.transifex.com/shattered-pixel/shattered-pixel-dungeon/).

## 🛠️ العمل مع الكود المصدري

إذا كنت ترغب في العمل مع الكود أو بناء اللعبة بنفسك، يمكنك العثور على الأدلة التالية في مجلد `/docs`:

- [التجميع لمنصة Android.](docs/getting-started-android.md)
    - **[إذا كنت تخطط للتوزيع على Google Play، يرجى قراءة نهاية هذا الدليل.](docs/getting-started-android.md#distributing-your-app)**
- [التجميع لمنصات سطح المكتب.](docs/getting-started-desktop.md)
- [التجميع لمنصة iOS.](docs/getting-started-ios.md)
- [التغييرات الموصى بها لإنشاء نسختك الخاصة.](docs/recommended-changes.md)

---

**ملاحظة**: هذا المستودع هو نسخة معدلة للتعريب العربي. للكود الأصلي والتقارير الرسمية، يرجى زيارة [المستودع الرسمي](https://github.com/00-Evan/shattered-pixel-dungeon).
