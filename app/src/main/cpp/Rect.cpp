//
// Created by Fabian Terhorst on 13.02.17.
//

#include "Rect.h"

void Rect::offsetBy(float x, float y) {
    this->origin.x += x;
    this->origin.y += y;
}
