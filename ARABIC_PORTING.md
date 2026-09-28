# ARABIC_PORTING.md — قائمة تعديلات محرك التعريب

1. مجلد جديد: SPD-classes/src/main/java/org/amr/arabic/
   - ArabicReshaper.java + ArabicUtilities.java (تشكيل وربط الحروف)

2. core/.../ui/RenderedTextBlock.java:
   - دالة containsArabic()
   - استدعاء ArabicUtilities.reshape() في text()
   - كتلة المرآة الأفقية في layout() (عكس مواضع الكلمات لكل سطر بصري)
   - إصلاح rightEdge في حساب عرض السطر للمحاذاة
   - تبديل LEFT/RIGHT alignment للعربية

3. desktop/.../DesktopPlatformSupport.java و android/.../AndroidPlatformSupport.java:
   - arabicFontGenerator (خط pixel_font_ar.ttf)
   - arabicMatcher بالنطاق [0600-06FF + FB50-FDFF + FE70-FEFF]
   - فحص arabicMatcher أولاً في getGeneratorForString()

4. core/.../messages/Languages.java: إضافة ARABIC للـ enum

5. الأصول: fonts/pixel_font_ar.ttf + ملفات *_ar.properties

6. ملاحظة: الترميز الخام UTF-8 يعمل بدون escape (المحرك يقرأ UTF-8)
