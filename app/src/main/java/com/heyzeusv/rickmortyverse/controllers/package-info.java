@EpoxyDataBindingPattern(rClass = R.class, layoutPrefix = "item_view")
package com.heyzeusv.rickmortyverse.controllers;

import com.airbnb.epoxy.EpoxyDataBindingPattern;
import com.heyzeusv.rickmortyverse.R;

/*
 *  Needed by Epoxy in order to be able to use DataBinding with it. Any layout that starts with
 *  "item_view" will tell Epoxy to generate a DataBinding model for it.
 *  [Epoxy Wiki](https://github.com/airbnb/epoxy/wiki/Data-Binding-Support)
 */