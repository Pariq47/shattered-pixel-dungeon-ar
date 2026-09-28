import os

def scan_files():
    print("=" * 70)
    print("🔍 تشخيص دعم اللغة العربية في Shattered Pixel Dungeon")
    print("=" * 70)

    # 1. Find where the reshaper is called
    print("\n[1] استخدام مكتبة التشكيل (ArabicUtilities / ArabicReshaper):")
    reshaper_used = False
    for root, _, files in os.walk('.'):
        if '.git' in root or 'build' in root:
            continue
        for file in files:
            if file.endswith('.java') and 'Arabic' not in file:
                path = os.path.join(root, file)
                try:
                    with open(path, 'r', encoding='utf-8') as f:
                        content = f.read()
                        if 'ArabicUtilities' in content or 'ArabicReshaper' in content:
                            reshaper_used = True
                            print(f"  ✅ يُستخدم في: {path}")
                except Exception:
                    pass
    if not reshaper_used:
        print("  ❌ لم يتم العثور على استخدام للمكتبة في أي ملف Java!")

    # 2. Check Font Loading in PlatformSupport
    print("\n[2] فحص تحميل الخطوط في PlatformSupport:")
    for root, _, files in os.walk('.'):
        if '.git' in root or 'build' in root:
            continue
        for file in files:
            if 'PlatformSupport' in file and file.endswith('.java'):
                path = os.path.join(root, file)
                print(f"  📄 {path}")
                try:
                    with open(path, 'r', encoding='utf-8') as f:
                        content = f.read()
                        if '.ttf' in content or 'arabic' in content.lower():
                            print("     -> يحتوي على إعدادات خطوط مخصصة.")
                        else:
                            print("     -> لا يحتوي على إعدادات واضحة للخط العربي.")
                except Exception:
                    pass

    # 3. Check RenderedTextBlock / PixelScene
    print("\n[3] فحص محركات عرض النصوص:")
    for root, _, files in os.walk('.'):
        if '.git' in root or 'build' in root:
            continue
        for file in files:
            if file in ['PixelScene.java', 'RenderedTextBlock.java', 'BitmapText.java', 'Messages.java']:
                path = os.path.join(root, file)
                print(f"  📄 {path}")
                try:
                    with open(path, 'r', encoding='utf-8') as f:
                        content = f.read()
                        if 'Arabic' in content or 'reshape' in content:
                            print("     ✅ يحتوي على معالجة النصوص العربية.")
                        else:
                            print("     ⚠️ لا يحتوي على استدعاءات تشكيل النصوص.")
                except Exception:
                    pass

    # 4. Check Assets for Fonts
    print("\n[4] البحث عن ملفات الخطوط (.ttf / .otf / .fnt):")
    fonts_found = []
    for root, _, files in os.walk('.'):
        if '.git' in root or 'build' in root:
            continue
        for file in files:
            if file.endswith(('.ttf', '.otf', '.fnt')):
                fonts_found.append(os.path.join(root, file))

    ar_fonts = [f for f in fonts_found if 'ar' in f.lower() or 'arabic' in f.lower()]
    if ar_fonts:
        print("  ✅ تم العثور على خطوط عربية:")
        for f in ar_fonts:
            print(f"     - {f}")
    else:
        print("  ❌ لم يتم العثور على خطوط عربية (يحتوي الاسم على 'ar' أو 'arabic').")

    if fonts_found:
        print("  💡 جميع ملفات الخطوط الموجودة:")
        for f in fonts_found[:15]:
            print(f"     - {f}")

    print("\n" + "=" * 70)
    print("📋 انسخ هذه النتيجة وأرسلها لي لأحدد سبب المشكلة بدقة.")
    print("=" * 70)

if __name__ == '__main__':
    scan_files()
