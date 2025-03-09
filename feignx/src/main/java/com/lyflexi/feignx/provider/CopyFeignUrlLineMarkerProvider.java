package com.lyflexi.feignx.provider;

import com.intellij.codeInsight.daemon.GutterIconNavigationHandler;
import com.intellij.codeInsight.daemon.GutterName;
import com.intellij.codeInsight.daemon.LineMarkerInfo;
import com.intellij.codeInsight.daemon.LineMarkerProviderDescriptor;
import com.intellij.notification.NotificationGroupManager;
import com.intellij.notification.NotificationType;
import com.intellij.openapi.editor.markup.GutterIconRenderer;
import com.intellij.openapi.ide.CopyPasteManager;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiMethod;
import com.lyflexi.feignx.cache.CacheManager;
import com.lyflexi.feignx.constant.MyIcons;
import com.lyflexi.feignx.utils.JavaResourceUtil;
import org.apache.commons.lang3.StringUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.datatransfer.StringSelection;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: hmly
 * @Date: 2025/3/9 17:42
 * @Project: feignx-plugin
 * @Version: 1.0.0
 * @Description:
 */

/*
 * 复制操作不属于RelatedItemLineMarkerProvider，IntelliJ 平台在新版 SDK 中推荐实现LineMarkerProviderDescriptor
 *
 * 1. 实现接口：LineMarkerProviderDescriptor
 * 2. 自定义构造LineMarkerInfo<PsiElement>：由于点击图标时执行复制操作不属于跳转导航操作NavigationGutterIconBuilder，而是需要手动构建 LineMarkerInfo
 *
 * */
public class CopyFeignUrlLineMarkerProvider extends LineMarkerProviderDescriptor {
    @Override
    public LineMarkerInfo<?> getLineMarkerInfo(@NotNull PsiElement element) {
        if (element instanceof PsiMethod && JavaResourceUtil.isElementWithinFeign(element)) {
            PsiMethod psiMethod = (PsiMethod) element;
            String url = CacheManager.getFeignPath(psiMethod);

            if (StringUtils.isNotBlank(url)) {
                GutterIconNavigationHandler<PsiElement> handler = (mouseEvent, elt) -> {
                    // 复制到剪贴板
                    CopyPasteManager.getInstance().setContents(new StringSelection(url));
                    // 显示通知（可选）
                    NotificationGroupManager.getInstance()
                            .getNotificationGroup("custom.notification.group")
                            .createNotification("URL copied to clipboard:\n" + url, NotificationType.INFORMATION)
                            .notify(psiMethod.getProject());
                };
                return new LineMarkerInfo<>(
                        element, // icon 放在哪个元素上，这里是方法名
                        element.getTextRange(),
                        MyIcons.STATEMENT_LINE_CLIPBOARD_ICON,
                        psi -> "Click to copy Feign-URL: " + url, // tooltip
                        handler,
                        GutterIconRenderer.Alignment.CENTER,
                        () -> "Copy Feign URL"
                );
            }
        }
        return null;
    }

    @Override
    public @Nullable("null means disabled") @GutterName String getName() {
        return "Copy Controller URL";
    }
}
