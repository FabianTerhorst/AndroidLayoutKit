//
// Created by Fabian Terhorst on 13.02.17.
//

#include "Size.h"

#include <math.h>

Size Size::decreaseBySize(Size *maxSize) {
    double width = fmin(this->width, maxSize->width);
    double height = fmin(this->height, maxSize->height);
    return Size(width, height);
}

Size Size::increaseBySize(Size *minSize) {
    double width = fmax(this->width, minSize->width);
    double height = fmax(this->height, minSize->height);
    return Size(width, height);
}

Size Size::decreaseByInsets(EdgeInsets *insets) {
    return Size(width - insets->left - insets->right, height - insets->top - insets->bottom);
}

Size Size::increaseByInsets(EdgeInsets *insets) {
    return Size(width + insets->left + insets->right, height + insets->top + insets->bottom);
}
